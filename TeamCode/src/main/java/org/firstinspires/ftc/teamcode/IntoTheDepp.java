package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Atoomolonis test")
// ticks per centemeter = 17.7914
public class IntoTheDepp extends LinearOpMode
{
  public MecanumDriveChassis driveChassis;
  public DistanceSensors distanceSensors;
  private final ElapsedTime runtime = new ElapsedTime();
  public ArmControl arm;
  int turnDistanceYaw = 900;
  IMU imu;
  
  
  // a timer for the various automation activities.
  @Override
  public void runOpMode()
  {
    arm = new ArmControl(hardwareMap, telemetry);
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
      //raise arm
      arm.armRaise();
      //hang sampel
      
      driveChassis.moveForward(20);
      
      //arm stuf heer
      arm.toggleGripper();
      driveChassis.moveBackward(10);
      driveChassis.strafeLeft(60);
      goAwayFromLeftWall(20);
      arm.armLower();
      arm.toggleGripper();
      //grabb sampil
      driveChassis.moveBackward(20);
      arm.armRaise();
      
      driveChassis.turnLeft();
      driveChassis.straighten(135);
      
      arm.toggleGripper();
      
      //arm dropp sampel n baskit
      driveChassis.straighten(0);
      driveChassis.moveForward(20);
      arm.armLower();
      arm.toggleGripper();
      
      //grrab sammpel
      driveChassis.moveBackward(20);
      arm.armRaise();
      driveChassis.turnLeft();
      driveChassis.straighten(135);
      
  
  
  
      arm.toggleGripper();
  
      //arm dropp sampel n baskit
      driveChassis.straighten(0);
      driveChassis.moveForward(20);
      //grrab sammpel
      arm.armLower();
      arm.toggleGripper();
      driveChassis.moveBackward(20);
      arm.armRaise();
      driveChassis.turnLeft();
      driveChassis.straighten(135);
      //arm dropp sampel n baskit
      arm.toggleGripper();
      driveChassis.straighten(0);
      driveChassis.moveForward(20);
      driveChassis.strafeRight(260);
      driveChassis.turnLeft();
      goTowardsBackWall(60);
      driveChassis.turnRight();
      driveChassis.moveBackward(20);
      
      telemetry.addLine("Moved Forward");
      telemetry.update();
      
      while (opModeIsActive())
      {
      
      }
      
      
      //telemetry.update();
      
    }
  }
  
  
  public void goAwayFromLeftWall(double distRight)
  
  {
    double distTravel = distRight - distanceSensors.leftDistance();
    driveChassis.strafeRight(distTravel);
  }
  public void goTowardsBackWall(double distBack)
  {
    double distTravel = distBack - distanceSensors.backDistance();
    driveChassis.moveBackward(distTravel);
  }
}
