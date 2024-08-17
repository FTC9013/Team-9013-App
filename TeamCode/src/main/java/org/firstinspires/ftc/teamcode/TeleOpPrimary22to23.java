package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Primary Tele-Op", group = "Linear Opmode")
@Disabled
public class TeleOpPrimary22to23 extends LinearOpMode
{
  private final ElapsedTime runtime = new ElapsedTime();
  
  // a timer for the various automation activities.
  @Override
  public void runOpMode()
  {
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    // setup a instance of our drive system
    // Declare OpMode members.
    MecanumDriveChassis driveChassis = new MecanumDriveChassis(hardwareMap, telemetry);
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
    runtime.reset();
    // run until the end of the match (driver presses STOP)
    while (opModeIsActive())
    {
      telemetry.addData("LStickY", gamepad1.left_stick_y * -1);
      telemetry.addData("LStickX", gamepad1.left_stick_x);
      telemetry.addData("vD: ", 1000);
      telemetry.update();
      
      driveChassis.drive(gamepad1.left_stick_y, gamepad1.left_stick_x,
        gamepad1.right_stick_x, gamepad1.left_bumper);
    }
  }
}