package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Sensor Test", group = "Linear Opmode")

public class SensorTest extends LinearOpMode
{
  public DistanceSensors propSensors;
  public PixelDropper pixelDropper;
  private final ElapsedTime runtime = new ElapsedTime();
  
  // a timer for the various automation activities.
  
  @Override
  public void runOpMode()
  {
    
    propSensors = new DistanceSensors(hardwareMap, telemetry);
    pixelDropper = new PixelDropper(hardwareMap, telemetry, "pixelDropperPurple");
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
      
      telemetry.addData("font distance is", propSensors.frontDistance());
      telemetry.addData("lef distance is", propSensors.leftDistance());
      telemetry.addData("rigt distance is", propSensors.rightDistance());
      telemetry.addData("bak distance is", propSensors.backDistance());
      telemetry.update();
      sleep(1500);
    }
  }
}