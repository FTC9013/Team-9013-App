package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class DacodAuto extends LinearOpMode
{
  public AprilTagCamera aprilTagCamera;
  public MecanumDrive robot;
  
  //public ConceptVisionColorSensor conceptVisionColorSensor;
  
  //set convertable constants
  Pose2d LAUNCH_POSITION = new Pose2d(-10, 14.5, Math.toRadians(-43));
  Pose2d SPIKE_PPG = new Pose2d(-8.75, 29, Math.toRadians(90));
  Pose2d SPIKE_PGP = new Pose2d(12, 31, Math.toRadians(90));
  Pose2d SPIKE_GPP = new Pose2d(35, 31, Math.toRadians(90));
  Pose2d SCANNING_POINT = new Pose2d(-16, 11.5, Math.toRadians(0));
  Double INTAKE = 45.0;
  Double BACK_UP = 31.0;
  Pose2d STARTING_FRONT = new Pose2d(61.25, 11.5, Math.toRadians(0));
  Pose2d STARTING_BACK = new Pose2d(-61.792, 38.285, Math.toRadians(0));
  Vector2d OUT_OF_LAUNCH = new Vector2d(0, 20);
  
  
  public Pose2d adjust(Pose2d pose)
  {
    if (amIBlue())
    {
      return new Pose2d(pose.position.x, pose.position.y * -1, -pose.heading.toDouble());
    } else
    {
      return pose;
    }
  }
  
  //convert double values
  public double adjust(Double para)
  {
    if (amIBlue())
    {
      return -para;
    } else
    {
      return para;
    }
  }
  
  //convert vectors
  public Vector2d adjust(Vector2d vector)
  {
    if (amIBlue())
    {
      return new Vector2d(vector.x, vector.y * -1);
    } else
    {
      return vector;
    }
  }
  
  public abstract boolean amIBlue();
  
  public void runOpMode()
  {
    
    //robot2 = new MecanumDriveChassis(hardwareMap, telemetry);
    aprilTagCamera = new AprilTagCamera(this);
    // conceptVisionColorSensor = new ConceptVisionColorSensor(hardwareMap, telemetry);
    Shooter shooter = new Shooter(hardwareMap, telemetry);
    telemetry.addLine("Initialized");
    telemetry.addLine("running auto yo");
    telemetry.update();
    
    //adjust all constants to usable values
    Pose2d ACTUAL_LAUNCH_POSITION = adjust(LAUNCH_POSITION);
    Pose2d ACTUAL_SPIKE_GPP = adjust(SPIKE_GPP);
    Pose2d ACTUAL_SPIKE_PGP = adjust(SPIKE_PGP);
    Pose2d ACTUAL_SPIKE_PPG = adjust(SPIKE_PPG);
    Pose2d ACTUAL_SCANNING_POINT = adjust(SCANNING_POINT);
    Pose2d ACTUAL_STARTING_POINT;
    PoseStorage.launchPose = ACTUAL_LAUNCH_POSITION;
    
    if (amIFront())
    {
      ACTUAL_STARTING_POINT = adjust(STARTING_FRONT);
    } else
    {
      ACTUAL_STARTING_POINT = adjust(STARTING_BACK);
    }
    robot = new MecanumDrive(hardwareMap, ACTUAL_STARTING_POINT);
    
    Vector2d ACTUAL_OUT_OF_LAUNCH = adjust(OUT_OF_LAUNCH);
    Double ACTUAL_INTAKE = adjust(INTAKE);
    Double ACTUAL_BACK_UP = adjust(BACK_UP);
    
    //4 paths and actions
    Action moveToScanningFirst = robot.actionBuilder(ACTUAL_STARTING_POINT)
      .setReversed(true)
      .lineToX(ACTUAL_SCANNING_POINT.position.x)
      .build();
    
    Action moveToScanningSecond = robot.actionBuilder(ACTUAL_STARTING_POINT)
      .splineToSplineHeading(ACTUAL_SCANNING_POINT, 0)
      .build();
    
    Action goToLaunch = robot.actionBuilder(ACTUAL_SCANNING_POINT)
      //preloaded
      .splineToLinearHeading(ACTUAL_LAUNCH_POSITION, 0)
      .build();
    
    Action gotoSpikePPG = robot.actionBuilder(ACTUAL_LAUNCH_POSITION)
      //spike GPP
      .splineToLinearHeading(ACTUAL_SPIKE_PPG, ACTUAL_SPIKE_PPG.heading)
      .stopAndAdd(shooter.startIntakingAction())
      .lineToY(ACTUAL_INTAKE)
      .stopAndAdd(shooter.stopAllMotorsAction())
      .splineToLinearHeading(ACTUAL_LAUNCH_POSITION, ACTUAL_LAUNCH_POSITION.heading)
      .stopAndAdd(shooter.shootGPP())
      .build();
    Action gotoSpikeGPP = robot.actionBuilder(ACTUAL_LAUNCH_POSITION)
      //spike GPP
      .splineToLinearHeading(ACTUAL_SPIKE_GPP, ACTUAL_SPIKE_GPP.heading)
      .stopAndAdd(shooter.startIntakingAction())
      .lineToY(ACTUAL_INTAKE)
      .stopAndAdd(shooter.stopAllMotorsAction())
      .splineToLinearHeading(ACTUAL_LAUNCH_POSITION, ACTUAL_LAUNCH_POSITION.heading)
      .stopAndAdd(shooter.shootGPP())
      .build();
    
    Action gotoSpikePGP = robot.actionBuilder(ACTUAL_LAUNCH_POSITION)
      //spike PGP
      .splineToLinearHeading(ACTUAL_SPIKE_PGP, ACTUAL_SPIKE_PGP.heading)
      .stopAndAdd(shooter.startIntakingAction())
      .lineToY(ACTUAL_INTAKE)
      .stopAndAdd(shooter.stopAllMotorsAction())
      .lineToY(ACTUAL_BACK_UP)
      .splineToLinearHeading(ACTUAL_LAUNCH_POSITION, ACTUAL_LAUNCH_POSITION.heading)
      .stopAndAdd(shooter.shootPGP())
      .build();
    Action collectPPG = robot.actionBuilder(ACTUAL_LAUNCH_POSITION)
      //spike PPG
      .splineToLinearHeading(adjust(new Pose2d(-13.75, SPIKE_PPG.position.y, Math.toRadians(90))), ACTUAL_SPIKE_PPG.heading)
      //.turnTo(0)
      .stopAndAdd(new SequentialAction(shooter.startIntakingAction(), shooter.conveyorAction()))
      //.strafeToConstantHeading(new Vector2d(-7.75, 36.7))
      .strafeTo(adjust(new Vector2d(-13.75, 36.7)))
      .strafeToConstantHeading(adjust(new Vector2d(-7.75, 36.7)))
      //.stopAndAdd(new SequentialAction(shooter.startIntakingAction(), shooter.conveyorAction()))
      .strafeToConstantHeading(adjust(new Vector2d(-7.75, INTAKE)))
      .splineToLinearHeading(ACTUAL_LAUNCH_POSITION, ACTUAL_LAUNCH_POSITION.heading)
      .build();
    Action getOut = robot.actionBuilder(ACTUAL_LAUNCH_POSITION)
      // out of launch_position
      .strafeTo(ACTUAL_OUT_OF_LAUNCH).build();
    Action go00 = robot.actionBuilder(ACTUAL_STARTING_POINT)
      .strafeToConstantHeading(new Vector2d(0, 0)).build();
    waitForStart();
    
    //Actions.runBlocking(go00);
    
    if (amIFront())
    {
      Actions.runBlocking(moveToScanningFirst);
      
    } else
    {
      Actions.runBlocking(moveToScanningSecond);
    }
    
    Motif motifPattern = aprilTagCamera.detectAprilTag();
    telemetry.addData("Found", motifPattern);
    telemetry.update();
    Actions.runBlocking(goToLaunch);
    //launchesyo preload
    
    if (motifPattern == Motif.GPP)
    {
      Actions.runBlocking(shooter.shootGPP());
      //Actions.runBlocking(collectPPG);
      //Actions.runBlocking(shooter.shootGPP());
    } else if (motifPattern == Motif.PGP)
    {
      Actions.runBlocking(shooter.shootPGP());
      //Actions.runBlocking(collectPPG);
      //Actions.runBlocking(shooter.shootPGP());
    } else
    {
      Actions.runBlocking(shooter.shootPPG());
      //Actions.runBlocking(collectPPG);
      //Actions.runBlocking(shooter.shootPPG());
    }
    
    Actions.runBlocking(collectPPG);
    shooter.conveyorBeltP.stopConveying();
    shooter.conveyorBeltG.stopConveying();
    
    
    //Actions.runBlocking();
    //go to the spike marks with correct motif first and collect artifacts
    if (motifPattern == Motif.GPP)
    {
      Actions.runBlocking(new SequentialAction(gotoSpikeGPP, gotoSpikePGP, gotoSpikePPG));
      
    } else if (motifPattern == Motif.PGP)
    {
      Actions.runBlocking(new SequentialAction(gotoSpikePGP, gotoSpikePPG, gotoSpikeGPP));
      
    } else if (motifPattern == Motif.PPG)
    {
      Actions.runBlocking(new SequentialAction(gotoSpikePPG, gotoSpikePGP, gotoSpikeGPP));
    }
    Actions.runBlocking(getOut);
    
    //record location
    PoseStorage.currentPose = robot.localizer.getPose();
    
  }
  
  
  public abstract boolean amIFront();
  
  private void moveForward(int timeDrivenMs)
  {
    robot.setDrivePowers(new PoseVelocity2d(new Vector2d(1, 0), 0));
    sleep(timeDrivenMs);
  }
  
  private void strafeRight(int timeDrivenMs)
  {
    robot.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 1), 0));
    sleep(timeDrivenMs);
  }
  
  private void strafeLeft(int timeDrivenMs)
  {
    robot.setDrivePowers(new PoseVelocity2d(new Vector2d(0, -1), 0));
    sleep(timeDrivenMs);
  }

//  private void rotate(double timeRotateMs)
//  {
//    robot.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 1));
//    sleep(timeRotateMs * 1000);
//
//  }
  
  private void moveBackward(int timeDrivenMs)
  {
    robot.setDrivePowers(new PoseVelocity2d(new Vector2d(-1, 0), 0));
    sleep(timeDrivenMs);
  }
  
  private void stopMoving()
  {
    robot.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
  }
}