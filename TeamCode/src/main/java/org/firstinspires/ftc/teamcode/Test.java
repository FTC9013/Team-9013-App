package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Test", group = "Linear Opmode")
public class Test extends LinearOpMode
{
  
  // public ConveyorBelt conveyorForward = null;
  public ConveyorBelt conveyorBelt = null;
  public Launcher launcher = null;
  public Intake intake = null;
  public ConveyorBelt conveyorBackward;
  
  //public MecanumDriveChassis ServoTest;
  @Override
  public void runOpMode()
  {
    telemetry.addData("Status", "Initialized");
    conveyorBelt = new ConveyorBelt(hardwareMap, telemetry, "purple");
    // conveyorForward = new ConveyorBelt(hardwareMap, telemetry, "forward");
    // conveyorBackward = new ConveyorBelt(hardwareMap, telemetry, "backward");
    launcher = new Launcher(hardwareMap, telemetry, "purple");
    intake = new Intake(hardwareMap, telemetry);
    waitForStart();
    while (opModeIsActive())
    {
        /*    if (gamepad1.right_bumper) {
                conveyorForward.startConveyingForward();
                telemetry.addLine("Pressing right bumper");
            }
            if (gamepad1.left_bumper) {
                conveyorBackward.startConveyingBackward();
                telemetry.addLine("Pressing left bumper");
            }
            if (gamepad1.a) {
                conveyorForward.startConveyingForward();
                telemetry.addLine("Pressing key A");
            }
*/
      if (gamepad1.x)
      {
        intake.stopIntaking();
        launcher.stopLaunching();
        conveyorBelt.stopConveying();
        telemetry.addLine("Stopping motors and servo");
      }
      if (gamepad1.y)
      {
        launcher.startLaunching();
      }
      if (gamepad1.right_bumper)
      {
        conveyorBelt.conveyForward();
        //telemetry.addLine("Pressing key X");
      } else if (gamepad1.right_trigger > 0)
      {
        conveyorBelt.conveyBackward();
      } else
      {
        conveyorBelt.stopConveying();
        
      }
      if (gamepad1.left_bumper)
      {
        intake.startIntaking();
      } else
      {
        intake.stopIntaking();
        
      }
      if (gamepad1.b)
      {
        
        launcher.startLaunching();
      } else
      {
        launcher.stopLaunching();
      }
      
      if (gamepad1.dpad_up)
      {
        launcher.launchSpeedIncreasing();
      }
      if (gamepad1.dpad_down)
      {
        launcher.launchSpeedDecreasing();
      }
      telemetry.update();
      
    }
  }
}

//we are working with a motor that does not spin infinitly so,... we   are   cooked
