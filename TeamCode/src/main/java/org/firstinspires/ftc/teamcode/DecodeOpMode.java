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
    boolean wasXPressed = false;
    boolean wasLeftBumperPressed = false;
    boolean wasBPressed = false;
    boolean wasdpadupPressed = false;
    boolean wasdpaddownPressed = false;
    boolean wasAPressed = false;
    boolean toggleDirection = false;
    boolean toggleTurbo = true;
    waitForStart();
    
    while (opModeIsActive())
    {
      double gampad1LeftStickY = -gamepad1.left_stick_y;
      double gampad1LeftStickX = -gamepad1.left_stick_x;
      double gampad1RightStickX = -gamepad1.right_stick_x;
      // toggles the speed and front side of robot
      if (!wasAPressed && gamepad1.a)
      {
        toggleDirection = !toggleDirection;
      }
      telemetry.addData("Frontside = ", toggleDirection ? "Shooter" : "Intake");
      
      
      wasAPressed = gamepad1.a;
      if (gamepad1.left_bumper && !wasLeftBumperPressed)
      {
        toggleTurbo = !toggleTurbo;
      }
      telemetry.addData("Turbomode = ", toggleTurbo ? "Slurbo" : "Normal");
      wasLeftBumperPressed = gamepad1.left_bumper;
      
      //flips to the opposite setting
      if (toggleDirection)
      {
        gampad1LeftStickY *= -1;
        gampad1LeftStickX *= -1;
      }
      if (toggleTurbo)
      {
        gampad1LeftStickY *= 0.67;
        gampad1LeftStickX *= 0.67;
        gampad1RightStickX *= 0.67;
      }
      drive.setDrivePowers(new PoseVelocity2d(
        new Vector2d(
          gampad1LeftStickY,
          gampad1LeftStickX
        ),
        gampad1RightStickX
      ));
      
      drive.updatePoseEstimate();
      
      Pose2d pose = drive.localizer.getPose();
      telemetry.addData("x", pose.position.x);
      telemetry.addData("y", pose.position.y);
      telemetry.addData("heading (deg)", Math.toDegrees(pose.heading.toDouble()));
      
      
      TelemetryPacket packet = new TelemetryPacket();
      packet.fieldOverlay().setStroke("#3F51B5");
      Drawing.drawRobot(packet.fieldOverlay(), pose);
      FtcDashboard.getInstance().sendTelemetryPacket(packet);
      
      telemetry.addData("Status", "Initialized");
      
      
      if (gamepad1.right_trigger > 0 || gamepad2.a)
      {
        shooter.startIntaking();
      } else if (gamepad1.right_bumper || gamepad2.y)
      {
        shooter.startIntakingBackwards();
        //robert is a nice person <3
      } else
      {
        shooter.intake.stopIntaking();
      }
      
      if (gamepad2.b && !wasBPressed)
      {
        shooter.launchWheelG.toggle();
      }
      wasBPressed = gamepad2.b;
      
      if (gamepad2.x && !wasXPressed)
      {
        shooter.launchWheelP.toggle();
      }
      wasXPressed = gamepad2.x;
      
      if (gamepad2.right_bumper)
      {
        shooter.conveyorBeltG.conveyForward();
        shooter.startIntaking();
        
        telemetry.addLine("Conveying Forward");
      } else if (gamepad2.right_trigger > 0)
      {
        shooter.conveyorBeltG.conveyBackward();
        telemetry.addLine("Conveying Backward");
      } else
      {
        shooter.conveyorBeltG.stopConveying();
        shooter.intake.stopIntaking();
      }
      
      
      if (gamepad2.left_bumper)
      {
        shooter.conveyorBeltP.conveyForward();
        shooter.startIntaking();
        
      } else if (gamepad2.left_trigger > 0)
      {
        shooter.conveyorBeltP.conveyBackward();
      } else
      {
        shooter.conveyorBeltP.stopConveying();
        shooter.intake.stopIntaking();
      }
      if (gamepad2.dpad_up && !wasdpadupPressed)
      {
        shooter.launchWheelG.launchSpeedIncreasing();
        shooter.launchWheelP.launchSpeedIncreasing();
        telemetry.addData("Increasing launch speed", gamepad2.dpad_up);
      }
      wasdpadupPressed = gamepad1.dpad_up;
      if (gamepad2.dpad_down && !wasdpaddownPressed)
      {
        shooter.launchWheelG.launchSpeedDecreasing();
        shooter.launchWheelP.launchSpeedDecreasing();
        telemetry.addData("Decreasing launch speed", gamepad2.dpad_down);
      }
      wasdpaddownPressed = gamepad1.dpad_down;
      shooter.launchWheelG.outputSpeed();
      telemetry.update();
    }
  }
}
