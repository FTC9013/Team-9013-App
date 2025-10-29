package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class DacodAuto extends LinearOpMode
{
  public AprilTagCamera aprilTagCamera;
  public MecanumDrive robot;
  public ConceptVisionColorSensor conceptVisionColorSensor;
  
  //set convertable constants
  Pose2d LAUNCH_POSITION = new Pose2d(-40.25, 15.5, Math.toRadians(-45));
  Pose2d SPIKE_GPP = new Pose2d(-11.25, 31, Math.toRadians(90));
  Pose2d SPIKE_PGP = new Pose2d(12, 31, Math.toRadians(90));
  Pose2d SPIKE_PPG = new Pose2d(35, 31, Math.toRadians(90));
  Pose2d SCANNING_POINT = new Pose2d(-31.25, 11.5, Math.toRadians(0));
  Double INTAKE = 45.0;
  Double BACK_UP = 31.0;
  Pose2d STARTING1 = new Pose2d(-61.25, 11.5, 0);
  Pose2d STARTING2 = new Pose2d(-56, 50, Math.toRadians(-45));
  Vector2d OUT_OF_LAUNCH = new Vector2d(-16, 38);
  
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
    robot = new MecanumDrive(hardwareMap, getStartingPose());
    aprilTagCamera = new AprilTagCamera(this);
    // conceptVisionColorSensor = new ConceptVisionColorSensor(hardwareMap, telemetry);
    Shooter shooter = new Shooter(hardwareMap, telemetry);
    telemetry.addLine("Initialized");
    telemetry.update();
    
    
    //adjust all constants to usable values
    Pose2d ACTUAL_LAUNCH_POSITION = adjust(LAUNCH_POSITION);
    Pose2d ACTUAL_SPIKE_GPP = adjust(SPIKE_GPP);
    Pose2d ACTUAL_SPIKE_PGP = adjust(SPIKE_PGP);
    Pose2d ACTUAL_SPIKE_PPG = adjust(SPIKE_PPG);
    Pose2d ACTUAL_SCANNING_POINT = adjust(SCANNING_POINT);
    Vector2d ACTUAL_OUT_OF_LAUNCH = adjust(OUT_OF_LAUNCH);
    Double ACTUAL_INTAKE = adjust(INTAKE);
    Double ACTUAL_BACK_UP = adjust(BACK_UP);
    
    
    //4 paths and actions
    Action moveToScanning = robot.actionBuilder(getStartingPose()).splineToLinearHeading(ACTUAL_SCANNING_POINT, Math.toRadians(0)).build();
    
    Action launchPreloaded = robot.actionBuilder(ACTUAL_SCANNING_POINT)
      //preloaded
      .splineToLinearHeading(ACTUAL_LAUNCH_POSITION, ACTUAL_LAUNCH_POSITION.heading).build();
    
    Action gotoSpikeGPP = robot.actionBuilder(ACTUAL_LAUNCH_POSITION)
      //spike GPP
      .splineToLinearHeading(ACTUAL_SPIKE_GPP, ACTUAL_SPIKE_GPP.heading).lineToY(ACTUAL_INTAKE).splineToLinearHeading(ACTUAL_LAUNCH_POSITION, ACTUAL_LAUNCH_POSITION.heading).build();
    
    Action gotoSpikePGP = robot.actionBuilder(ACTUAL_LAUNCH_POSITION)
      //spike PGP
      .splineToLinearHeading(ACTUAL_SPIKE_PGP, ACTUAL_SPIKE_PGP.heading).lineToY(ACTUAL_INTAKE).lineToY(ACTUAL_BACK_UP).splineToLinearHeading(ACTUAL_LAUNCH_POSITION, ACTUAL_LAUNCH_POSITION.heading).build();
    
    Action gotoSpikePPG = robot.actionBuilder(ACTUAL_LAUNCH_POSITION)
      //spike PPG
      .splineToLinearHeading(ACTUAL_SPIKE_PPG, ACTUAL_SPIKE_PPG.heading).lineToY(ACTUAL_INTAKE).lineToY(ACTUAL_BACK_UP).splineToLinearHeading(ACTUAL_LAUNCH_POSITION, ACTUAL_LAUNCH_POSITION.heading).build();
    
    Action getOut = robot.actionBuilder(ACTUAL_LAUNCH_POSITION)
      // out of launch_position
      .strafeTo(ACTUAL_OUT_OF_LAUNCH).build();
    
    
    waitForStart();
    Actions.runBlocking(moveToScanning);
    Motif motifPattern = aprilTagCamera.detectAprilTag();
    Actions.runBlocking(launchPreloaded);
    
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
  }
  
  
  public abstract Pose2d getStartingPose();
  
  /*
   Auto routine:
   call findObelisk()
   do something with the aprilTagCamera.detectAprilTag() method
   call goToLaunchPosition()
   call launcher.startLaunching()
   call collectArtifacts()
   call goToLaunchPosition()
   call launcher.startLaunching()
   repeat up 3 steps above up to 3 times
  */
}