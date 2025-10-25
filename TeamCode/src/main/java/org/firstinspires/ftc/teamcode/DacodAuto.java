package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class DacodAuto extends LinearOpMode
{
  //Need to add in movement code
  public AprilTagCamera aprilTagCamera;
  public ConveyorBelt conveyorBelt;
  public Launcher launcher;
  public MecanumDrive robot;
  public ConceptVisionColorSensor conceptVisionColorSensor;
  
  Pose2d LAUNCH_POSITION = new Pose2d(-40.25, 15.5, Math.toRadians(-45));
  Pose2d SPIKE1 = new Pose2d(-11.25, 31, Math.toRadians(90));
  Pose2d SPIKE2 = new Pose2d(12, 31, Math.toRadians(90));
  Pose2d SPIKE3 = new Pose2d(35, 31, Math.toRadians(90));
  Pose2d SCANNING_POINT = new Pose2d(-31.25, 11.5, Math.toRadians(0));
  Double INTAKE = 45.0;
  Double BACK_UP = 31.0;
  Pose2d STARTING1 = new Pose2d(-61.25, 11.5, 0);
  Pose2d STARTING2 = new Pose2d(-56, 50, Math.toRadians(-45));
  
  /*
  md.runAction(md.getDrive().actionBuilder(STARTING2)
    .splineToLinearHeading(SCANNING_POINT, Math.toRadians(0))
    .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
    .splineToLinearHeading(SPIKE1, SPIKE1.heading)
      .lineToY(INTAKE)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .splineToLinearHeading(SPIKE2, SPIKE2.heading)
      .lineToY(INTAKE)
      .lineToY(BACK_UP)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .splineToLinearHeading(SPIKE3, SPIKE3.heading)
      .lineToY(INTAKE)
      .lineToY(BACK_UP)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .strafeTo(new Vector2d(-16, 38))
  .build());
  */
  public Pose2d adjust(Pose2d pose)
  
  {
    if (amIBlue())
    {
      return new Pose2d(pose.position.x, pose.position.y * -1, 0);
    } else
    {
      return pose;
    }
  }
  
  public abstract boolean amIBlue();
  
  public void runOpMode()
  {
    robot = new MecanumDrive(hardwareMap, getStartingPose());
    aprilTagCamera = new AprilTagCamera(this);
    conceptVisionColorSensor = new ConceptVisionColorSensor(hardwareMap, telemetry);
    conveyorBelt = new ConveyorBelt(hardwareMap, telemetry);
    launcher = new Launcher(hardwareMap, telemetry);
    telemetry.addLine("Initialized");
    telemetry.update();
    
    //4 paths and actions
    Action MoveToScanning = robot.actionBuilder(getStartingPose())
        .splineToLinearHeading(adjust(SCANNING_POINT), Math.toRadians(0)).build();
    
    
    Action GotoSpikes = robot.actionBuilder(adjust(SCANNING_POINT))
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .splineToLinearHeading(SPIKE1, SPIKE1.heading)
      .lineToY(INTAKE)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .splineToLinearHeading(SPIKE2, SPIKE2.heading)
      .lineToY(INTAKE)
      .lineToY(BACK_UP)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .splineToLinearHeading(SPIKE3, SPIKE3.heading)
      .lineToY(INTAKE)
      .lineToY(BACK_UP)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .strafeTo(new Vector2d(-16, 38))
      .build());
      


    waitForStart();
    Motif motifPattern = aprilTagCamera.detectAprilTag();
    
    
    //the auto method
    //findObelisk();
//    aprilTagCamera.detectAprilTag();//we need to improve on this line later
    launchArtifacts();
    collectArtifacts(aprilTagCamera.detectAprilTag());
    launchArtifacts();
    collectArtifacts(aprilTagCamera.detectAprilTag());
    launchArtifacts();
  }
  
  public void launchPreloaded()
  {
  
  }
  
  public abstract Pose2d getStartingPose();
  
  public void collectArtifacts(Motif desiredSpike)
  {
    //go to the spike marks and collect artifacts
    if (desiredSpike == Motif.GPP)
    {
    
    } else if (desiredSpike == Motif.PPG)
    {
    
    } else if (desiredSpike == Motif.PGP)
    {
    
    }
    
    
  }
  
  /*
   Auto routine:z
   call findObelisk()
   do something with the aprilTagCamera.detectAprilTag() method
   call goToLaunchPosition()
   call launcher.startLaunching()
   call collectArtifacts()
   call goToLaunchPosition()
   call launcher.startLaunching()
   repeat up 3 steps above up to 3 times
  */
  public void launchArtifacts()
  {
    //goToLaunchPosition();
    launcher.startLaunching();
    launcher.stopLaunching();
  }
}

