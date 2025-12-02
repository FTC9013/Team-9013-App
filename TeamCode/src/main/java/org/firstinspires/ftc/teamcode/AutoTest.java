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
    Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(0));
    MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
    
    
    // vision here that outputs position
    int visionOutputPosition = 1;
    
    Action tab1 = drive.actionBuilder(initialPose)
      .splineTo(new Vector2d(33, 0), 0)
      .waitSeconds(2)
      .build();
    
    
    while (!isStopRequested() && !opModeIsActive())
    {
      int position = visionOutputPosition;
      telemetry.addData("Position during Init", position);
      telemetry.update();
    }
    
    int startPosition = visionOutputPosition;
    telemetry.addData("Starting Position", startPosition);
    telemetry.update();
    waitForStart();
    
    if (isStopRequested()) return;
    
    
    Actions.runBlocking(tab1);
  }
}
