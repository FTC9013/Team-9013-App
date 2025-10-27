package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
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
    Action moveToScanning = robot.actionBuilder(getStartingPose())
      .splineToLinearHeading(adjust(SCANNING_POINT), Math.toRadians(0)).build();
    
    
    Action launchPreloaded = robot.actionBuilder(adjust(SCANNING_POINT))
      //preloaded
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .build();
    
    Action gotoSpikeGPP = robot.actionBuilder(adjust(LAUNCH_POSITION))
      //spike GPP
      .splineToLinearHeading(SPIKE_GPP, SPIKE_GPP.heading)
      .lineToY(INTAKE)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .build();
    
    Action gotoSpikePGP = robot.actionBuilder(adjust(LAUNCH_POSITION))
      //spike PGP
      .splineToLinearHeading(SPIKE_PGP, SPIKE_PGP.heading)
      .lineToY(INTAKE)
      .lineToY(BACK_UP)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .build();
    
    Action gotoSpikePPG = robot.actionBuilder(adjust(LAUNCH_POSITION))
      //spike PPG
      .splineToLinearHeading(SPIKE_PPG, SPIKE_PPG.heading)
      .lineToY(INTAKE)
      .lineToY(BACK_UP)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .build();
    
    Action getOut = robot.actionBuilder(adjust(LAUNCH_POSITION))
      // out of launch_position
      .strafeTo(OUT_OF_LAUNCH)
      .build();
    
    
    waitForStart();
    Actions.runBlocking(moveToScanning);
    Motif motifPattern = aprilTagCamera.detectAprilTag();
    
    
    //go to the spike marks and collect artifacts
    if (motifPattern == Motif.GPP)
    {
      Actions.runBlocking(
        new SequentialAction(
          gotoSpikeGPP, gotoSpikePGP, gotoSpikePPG));
      
    } else if (motifPattern == Motif.PGP)
    {
      Actions.runBlocking(
        new SequentialAction(
          gotoSpikePGP, gotoSpikePPG, gotoSpikeGPP));
      
    } else if (motifPattern == Motif.PPG)
    {
      Actions.runBlocking(
        new SequentialAction(
          gotoSpikePPG, gotoSpikePGP, gotoSpikeGPP));
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

