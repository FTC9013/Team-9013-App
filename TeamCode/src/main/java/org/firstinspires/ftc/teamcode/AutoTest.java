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
    Pose2d initialPose = new Pose2d(-67, 41, Math.toRadians(0));
    Pose2d startingBack = new Pose2d(-54, 49, Math.toRadians(-45));
    MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
    Pose2d currentPose = new Pose2d(0, 0, 0);
    Action goToCenter = drive.actionBuilder(startingBack)
      .strafeToLinearHeading(new Vector2d(0, 0), 0)
      .build();
    Action tab1 = drive.actionBuilder(initialPose)
      .splineTo(new Vector2d(33, 20), 0)
      .lineToX(21)
      .splineTo(new Vector2d(0, 41), 0)
      .waitSeconds(2)
      .build();
    //Action trackPosition = drive.actionBuilder(new Pose2d(0,0,0)).build();
    waitForStart();

//    while (opModeIsActive())
//    {
//      currentPose = drive.localizer.getPose();
//      telemetry.addData("Current Position: ", currentPose);
//      telemetry.update();
//    }
//
    
    Actions.runBlocking(goToCenter);
    
  }
}
