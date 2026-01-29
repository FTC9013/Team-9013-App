package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
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
    Action goToLaunchPos = null;
    Lift lift = new Lift(hardwareMap, telemetry);
    //define button pressing
    MecanumDrive drive = new MecanumDrive(hardwareMap, PoseStorage.currentPose);
    boolean wasXPressed = false;
    boolean wasLeftBumperPressed = false;
    boolean wasBPressed = false;
    boolean wasdpadupPressed = false;
    boolean wasdpaddownPressed = false;
    boolean wasAPressed = false;
    boolean toggleDirection = false;
    boolean toggleSlurbo = false;
    waitForStart();
    
    while (opModeIsActive())
    
    {
      TelemetryPacket packet = new TelemetryPacket();
      PoseStorage.currentPose = drive.localizer.getPose();
      
      //public ConveyorBelt conveyorForward;
      
      //define button pressing
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
      
      if (gamepad1.y)
      {
        
        if (goToLaunchPos == null)
        {
          goToLaunchPos = drive.actionBuilder(PoseStorage.currentPose)
            .strafeToLinearHeading(new Vector2d(PoseStorage.launchPose.position.x, PoseStorage.launchPose.position.y), PoseStorage.launchPose.heading)
            .build();
        }
        boolean running = goToLaunchPos.run(packet);
        if (!running)
        {
          goToLaunchPos = null;
        }
        telemetry.addLine("moving to launch position");
      }
      
      if (gamepad1.left_bumper && !wasLeftBumperPressed)
      {
        toggleSlurbo = !toggleSlurbo;
      }
      telemetry.addData("Slurbomode = ", toggleSlurbo ? "Slurbo" : "Normal");
      wasLeftBumperPressed = gamepad1.left_bumper;
      
      if (getRuntime() >= 100)
      {
        if (gamepad1.x && gamepad1.b)
        {
          lift.startMovingUp();
        } else
        {
          lift.stop();
        }
      }
      //flips to the opposite setting
      if (toggleDirection)
      {
        gampad1LeftStickY *= -1;
        gampad1LeftStickX *= -1;
      }
      //slowing down
      if (toggleSlurbo)
      {
        gampad1LeftStickY *= 0.67;
        gampad1LeftStickX *= 0.67;
        gampad1RightStickX *= 0.67;
      }
      
      if (getDistance(PoseStorage.currentPose.position, PoseStorage.launchPose.position) <= 15)
      {
        //change speed
        gampad1LeftStickY *= 0.67;
        gampad1LeftStickX *= 0.67;
        gampad1RightStickX *= 0.67;
        telemetry.addLine("Entered within proximity of launch zone. Slowing down");
      }
      if (!gamepad1.y)
      {
        drive.setDrivePowers(new PoseVelocity2d(
          new Vector2d(
            gampad1LeftStickY,
            gampad1LeftStickX
          ),
          gampad1RightStickX
        ));
      }
      
      
      drive.updatePoseEstimate();
      
      Pose2d pose = drive.localizer.getPose();
      telemetry.addData("x", pose.position.x);
      telemetry.addData("y", pose.position.y);
      telemetry.addData("heading (deg)", Math.toDegrees(pose.heading.toDouble()));
      
      
      packet.fieldOverlay().setStroke("#3F51B5");
      Drawing.drawRobot(packet.fieldOverlay(), pose);
      FtcDashboard.getInstance().sendTelemetryPacket(packet);
      
      telemetry.addData("Status", "Initialized");
      
      //intake
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
      
      //green launcher
      if (gamepad2.b && !wasBPressed)
      {
        shooter.launchWheel.toggle();
      }
      wasBPressed = gamepad2.b;
      
      //purple launcher
      if (gamepad2.x && !wasXPressed)
      {
        shooter.launchWheel.toggle();
      }
      wasXPressed = gamepad2.x;
      
      //green conveyor forward
      if (gamepad2.right_bumper)
      {
        shooter.conveyorBeltG.conveyForward();
        shooter.startIntaking();
        
        telemetry.addLine("Conveying Forward");
        //backward
      } else if (gamepad2.right_trigger > 0)
      {
        shooter.conveyorBeltG.conveyBackward();
        telemetry.addLine("Conveying Backward");
      } else
      {
        shooter.conveyorBeltG.stopConveying();
        shooter.intake.stopIntaking();
      }
      
      //purple conveyor forward
      if (gamepad2.left_bumper)
      {
        shooter.conveyorBeltP.conveyForward();
        shooter.startIntaking();
        
        //backward
      } else if (gamepad2.left_trigger > 0)
      {
        shooter.conveyorBeltP.conveyBackward();
      } else
      {
        shooter.conveyorBeltP.stopConveying();
        shooter.intake.stopIntaking();
      }
      //increase launch speed
      if (gamepad2.dpad_up && !wasdpadupPressed)
      {
        shooter.launchWheel.launchSpeedIncreasing();
        telemetry.addData("Increasing launch speed", gamepad2.dpad_up);
      }
      wasdpadupPressed = gamepad2.dpad_up;
      
      //decrease launch speed
      if (gamepad2.dpad_down && !wasdpaddownPressed)
      {
        shooter.launchWheel.launchSpeedDecreasing();
        telemetry.addData("Decreasing launch speed", gamepad2.dpad_down);
      }
      //speed is same for both, even though code says green
      wasdpaddownPressed = gamepad2.dpad_down;
      shooter.launchWheel.printOutputSpeed();
      //pose saving
      telemetry.addData("Current x position", PoseStorage.currentPose.position.x);
      telemetry.addData("Current y position", PoseStorage.currentPose.position.y);
      telemetry.addData("Current heading", PoseStorage.currentPose.heading);
      telemetry.update();
      
    }
  }
  
  double getDistance(Vector2d start, Vector2d finish)
  {
    return Math.sqrt((start.x - finish.x) * (start.x - finish.x) + (start.y - finish.y) * (start.y - finish.y));
    //continue doing pythag for distance
  }
}
