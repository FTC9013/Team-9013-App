package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Config
@Autonomous(name = "AutoTest", group = "Autonomous")
public class AutoTest extends LinearOpMode
{
  
  @Override
  public void runOpMode()
  {
    //Math.toRadians(-53.972626614896)
    //Pose2d initialPose = new Pose2d(-67, 41, Math.toRadians(0));
    //Pose2d startingBack = new Pose2d(-50.43, 51.32, Math.toRadians(360 - 53.972626614896));
    Pose2d startingBack = new Pose2d(-61.792, 38.285, 0);
    Pose2d startingFront = new Pose2d(61.652, 15.125, 0);
    Pose2d currentPose = new Pose2d(0, 0, 0);
    MecanumDrive drive = new MecanumDrive(hardwareMap, startingFront);
    
    Action goToCenter = drive.actionBuilder(startingFront)
      .strafeToConstantHeading(new Vector2d(0, 0))
      
      .build();
    /*
    Action tab1 = drive.actionBuilder(initialPose)
      .splineTo(new Vector2d(33, 20), 0)
      .lineToX(21)
      .splineTo(new Vector2d(0, 41), 0)
      .waitSeconds(2)
      .build();
      
     */
    waitForStart();
    /*
    Action trackPosition = drive.actionBuilder(new Pose2d(0, 0, 0)).build();
    
    
    while (opModeIsActive())
    {
      currentPose = drive.localizer.getPose();
      telemetry.addData("Current Position: ", currentPose);
      telemetry.update();
      drive.updatePoseEstimate();
    }
    
    */
    Actions.runBlocking(goToCenter);
    
  }
}
