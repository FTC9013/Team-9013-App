package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Atoomolonis test")
// ticks per centemeter = 17.7914
public class IntoTheDepp extends LinearOpMode
{
  public MecanumDriveChassis driveChassis;
  public DistanceSensors distanceSensors;
  private final ElapsedTime runtime = new ElapsedTime();
  
  
  // a timer for the various automation activities.
  @Override
  public void runOpMode()
  {
    
    distanceSensors = new DistanceSensors(hardwareMap, telemetry);
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    // setup a instance of our drive system
    // Declare OpMode members.
    driveChassis = new MecanumDriveChassis(hardwareMap, telemetry);
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
    runtime.reset();
    // run until the end of the match (driver presses STOP)
    
    if (opModeIsActive())
    {
      
      
      telemetry.addLine("Moved Forward");
      telemetry.update();
      
      while (opModeIsActive())
      {
      
      }
      
      
      //telemetry.update();
      
    }
  }
  
  
}
