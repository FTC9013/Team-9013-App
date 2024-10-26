package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Pixel Dropper Test", group = "Linear Opmode")
@Disabled
public class PixelDropperTest extends LinearOpMode
{
  public DistanceSensors propSensors;
  public PixelDropper pixelDropperPurple;
  public PixelDropper pixelDropperYellow;
  private final ElapsedTime runtime = new ElapsedTime();
  
  // a timer for the various automation activities.
  @Override
  
  public void runOpMode()
  {
    pixelDropperPurple = new PixelDropper(hardwareMap, telemetry, "pixelDropperPurple");
    pixelDropperYellow = new PixelDropper(hardwareMap, telemetry, "pixelDropperYellow");
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    // setup a instance of our drive system
    // Declare OpMode members.
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
    runtime.reset();
    // run until the end of the match (driver presses STOP)
    while (opModeIsActive())
    {
      if (gamepad1.right_bumper)
      {
        pixelDropperPurple.drop_pixel();
      }
      if (gamepad1.left_bumper)
      {
        pixelDropperPurple.resetDropper();
      }
      if (gamepad1.left_bumper && gamepad1.left_trigger > 0.75)
      {
        pixelDropperYellow.drop_pixel();
      }
      if (gamepad1.right_bumper && gamepad1.right_trigger > 0.75)
      {
        pixelDropperYellow.drop_pixel();
      }
      telemetry.update();
    }
  }
}