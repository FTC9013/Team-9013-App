/* Created by Phil Malone. 2023.
    This class illustrates my simplified Odometry Strategy.
    It implements basic straight line motions but with heading and drift controls to limit drift.
    See the readme for a link to a video tutorial explaining the operation and limitations of the code.
 */

package org.firstinspires.ftc.teamcode.oldcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
 * This OpMode illustrates an autonomous opmode using simple Odometry
 * All robot functions are performed by an external "Robot" class that manages all hardware interactions.
 * Pure Drive or Strafe motions are maintained using two Odometry Wheels.
 * The IMU gyro is used to stabilize the heading during all motions
 */
@Disabled
@Autonomous(name = "0d0m3try 1nch35", group = "Mr. Phil")
public class OdometrySampleAutonomous extends LinearOpMode
{
  // get an instance of the "Robot" class.
  private SimplifiedOdometryRobotInches driveChassisOdom = new SimplifiedOdometryRobotInches(this);
  static final double DEFAULT_POWER = 0.8;
  static final double DEFAULT_HOLD_TIME = 0;
  static final double DEFAULT_TIMEOUT = 4;
  
  @Override
  public void runOpMode()
  {
    // Initialize the robot hardware & Turn on telemetry
    driveChassisOdom.initialize(false);
    
    // Wait for driver to press start
    telemetry.addData(">", "Touch Play to run Auto");
    telemetry.update();
    
    waitForStart();
    driveChassisOdom.resetHeading();  // Reset heading to set a baseline for Auto
    
    // Run Auto if stop was not pressed.
    if (opModeIsActive())
    {
      telemetry.addLine("we running now");
      telemetry.update();
      // Note, this example takes more than 30 seconds to execute, so turn OFF the auto timer.
      
      // Drive a large rectangle, turning at each corner
      drive(64);
      telemetry.addLine("sigma sigma boy");
      //arm.extendForTime(0.4);
      //sleep(500);
      sleep(200);
      drive(-39);
      telemetry.addLine("Strafing left");
      telemetry.update();
      strafe(88);
      sleep(800);
      telemetry.addLine("Turning");
      telemetry.update();
      turn(90);
      telemetry.addLine("Strafing");
      telemetry.update();
      drive(48);
      strafe(21);
      
      telemetry.addLine("sample dropped");
      telemetry.update();
      
      drive(-60);
      sleep(250);
      turn(0);
      
      drive(95);
      turn(-90);
      
      drive(20);
      /*robot.turnTo(-90, 0.45, 0.05);
      robot.drive(30, 0.60, 0.05);
      robot.turnTo(-180, 0.45, 0.05);
      robot.drive(30, 0.60, 0.05);
      robot.turnTo(-270, 0.45, 0.05);
      robot.drive(30, 0.60, 0.05);
      robot.turnTo(0, 0.45, 0.05);
      
      sleep(500);
      
      // Drive the path again without turning.
      robot.drive(30, 0.60, 0.1);
      robot.strafe(-30, 0.60, 0.1);
      robot.drive(-30, 0.60, 0.1);
      robot.strafe(30, 0.60, 0.1);*/
    }
  }
  
  public void drive(double distanceCm)
  {
    driveChassisOdom.drive(distanceCm, DEFAULT_POWER, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
  }
  
  public void strafe(double distanceCm)
  {
    driveChassisOdom.strafe(distanceCm, DEFAULT_POWER, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
  }
  
  public void turn(double degree)
  {
    driveChassisOdom.turnTo(degree, DEFAULT_POWER, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
  }
}
// im bigger and better and ill host the famous sumo reslting fight of tony and grayson