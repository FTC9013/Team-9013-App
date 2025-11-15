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
  public ConveyorBelt conveyorBelt = null;
  public Launcher launcher = null;
  public Intake intake = null;
  public ConveyorBelt conveyorForward;
  public ConveyorBelt conveyorBackward;
  
  @Override
  public void runOpMode() throws InterruptedException
  {
    //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    Shooter shooter = new Shooter(hardwareMap, telemetry);
    //conveyorBelt = new ConveyorBelt(hardwareMap, telemetry);
    //greenConveyor = new ConveyorBelt(hardwareMap, telemetry, "green");
    //purpleConveyor = new ConveyorBelt(hardwareMap, telemetry, "purple");
    
    //conveyors are second intake
    conveyorForward = new ConveyorBelt(hardwareMap, telemetry, "forward");
    conveyorBackward = new ConveyorBelt(hardwareMap, telemetry, "backward");
    launcher = new Launcher(hardwareMap, telemetry);
    //intake is first intake
    intake = new Intake(hardwareMap, telemetry);
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
      
      //waitForStart();
      //  while (opModeIsActive()) {
      
      if (gamepad2.left_bumper)
      {
        conveyorForward.startConveyingForward();
        conveyorBackward.startConveyingBackward();
        //telemetry.addLine("Pressing right bumper");
      } else if (gamepad2.left_trigger > 0)
      {
        conveyorForward.startConveyingBackward();
        conveyorBackward.startConveyingForward();
      } else
      {
        conveyorBackward.stopConveying();
        conveyorForward.stopConveying();
      }
      //if (gamepad1.right_trigger > 0)
      
      //            if (gamepad1.left_bumper) {
//                telemetry.addLine("Pressing left bumper");
//            }
      
      // if (gamepad1.right_bumper) {
      //     conveyorBelt.startConveyingIncreasing();
      //     telemetry.addLine("Pressing right bumper");
      // }
      // if (gamepad1.left_bumper) {
      //    conveyorBelt.startConveyingDecreasing();
      //   telemetry.addLine("Pressing left bumper");
      //  }
      //if (gamepad1.a) {
      //     conveyorBelt.startConveying();
      //    telemetry.addLine("Pressing key A");
      // }

//      if (gamepad1.x)
//      {
//        intake.stopIntaking();
//        launcher.stopLaunching();
//        conveyorForward.stopConveying();
//        conveyorForward.stopConveying();
//        telemetry.addLine("Stopping motors and servo");
//      }
      if (gamepad2.a)
      {
        launcher.startLaunching();
      } else if (gamepad2.b)
      {
        launcher.startLaunchingBackward();
      } else
      {
        launcher.stopLaunching();
      }
      
      
      if (gamepad1.right_bumper)
      {
        intake.startIntaking();
        //telemetry.addLine("Pressing key X");
      } else if (gamepad1.right_trigger > 0)
      {
        intake.startIntakingBackward();
      } else
      {
        intake.stopIntaking();
      }
      
      // if (gamepad1.dpad_up) {
      //    launcher.launchSpeedIncreasing();
      // }
      // if (gamepad1.dpad_down) {
      //     launcher.launchSpeedDecreasing();
      //  }
      telemetry.update();
    }
  }
}
