package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Decode", group = "Linear Opmode")
public class DecodeOpMode extends LinearOpMode
{
  
  //public ConveyorBelt conveyorForward;
  //public ConveyorBelt conveyorBackward;
  
  @Override
  public void runOpMode() throws InterruptedException
  {
    //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    Shooter shooter = new Shooter(hardwareMap, telemetry);
    
    MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
    
    waitForStart();
    
    while (opModeIsActive())
    {
      drive.setDrivePowers(new PoseVelocity2d(
        new Vector2d(
          -gamepad1.left_stick_y,
          -gamepad1.left_stick_x
        ),
        -gamepad1.right_stick_x
      ));
      
      drive.updatePoseEstimate();
      
      Pose2d pose = drive.localizer.getPose();
      telemetry.addData("x", pose.position.x);
      telemetry.addData("y", pose.position.y);
      telemetry.addData("heading (deg)", Math.toDegrees(pose.heading.toDouble()));
      telemetry.update();
      
      TelemetryPacket packet = new TelemetryPacket();
      packet.fieldOverlay().setStroke("#3F51B5");
      Drawing.drawRobot(packet.fieldOverlay(), pose);
      FtcDashboard.getInstance().sendTelemetryPacket(packet);
      
      telemetry.addData("Status", "Initialized");
      
      
      if (gamepad2.a)
      {
        shooter.startIntaking();
      } else if (gamepad2.y)
      {
        shooter.startIntakingBackwards();
      } else
      {
        shooter.intake.stopIntaking();
      }
      
      if (gamepad2.b)
      {
        shooter.startLaunchingG();
      } else
      {
        shooter.stopLaunchingG();
      }
      
      if (gamepad2.x)
      {
        shooter.startLaunchingP();
      } else
      {
        shooter.stopLaunchingP();
      }
      
      
      if (gamepad1.right_bumper)
      {
        shooter.conveyorBeltG.conveyForward();
      } else if (gamepad1.right_trigger > 0)
      {
        shooter.conveyorBeltG.conveyBackward();
      } else
      {
        shooter.conveyorBeltG.stopConveying();
      }
      
      if (gamepad1.left_bumper)
      {
        shooter.conveyorBeltP.conveyForward();
      } else if (gamepad1.left_trigger > 0)
      {
        shooter.conveyorBeltP.conveyBackward();
      } else
      {
        shooter.conveyorBeltP.stopConveying();
      }
      
      telemetry.update();
    }
  }
}
