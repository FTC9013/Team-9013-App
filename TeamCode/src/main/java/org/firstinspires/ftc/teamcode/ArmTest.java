package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Test Auto")

public class ArmTest extends LinearOpMode

{
  public SimplifiedOdometryRobotInches driveChassis;
  static final double DEFAULT_POWER = 0.8;
  static final double DEFAULT_HOLD_TIME = 0;
  static final double DEFAULT_TIMEOUT = 4;
  private ElapsedTime runTimer = new ElapsedTime();
  public ArmControl arm;
  
  @Override
  
  public void runOpMode()
  {
    waitForStart();
    arm = new ArmControl(this);
    driveChassis = new SimplifiedOdometryRobotInches(this);
    driveChassis.initialize(true);
    driveChassis.resetHeading();
    driveChassis.drive(100, DEFAULT_POWER, DEFAULT_HOLD_TIME, 7);
    
    
    
    
    /*testTurn(1, 0.018, 3, 1, 0.25);
    testTurn(0.8, 0.018, 3, 1, 0.25);
    testTurn(0.8, 0.018, 1.5, 1, 0.25);
    testTurn(0.6, 0.018, 3, 1, 0.25);
    testTurn(0.6, 0.018, 1.5, 1, 0.25);
    /*extensionTest(1885);
    extensionTest(1890);
    extensionTest(1895);
    extensionTest(1900);
    extensionTest(1905);
    */
    
    
    //testDrive(0.8, 0.03, 1, 0.5, 0.4);
    //testStrafe(1, 0.03, 1, 0.25, 0.2);
    //testStrafe(1, 0.03, 1, 0.2, 0.15);
    //testStrafe(0.8, 0.03, 1, 0.25, 0.2);
    //testStrafe(0.8, 0.03, 0.5, 0.25, 0.2);
    //testStrafe(0.8, 0.03, 2, 0.25, 0.2);
    //testStrafe(0.8, 0.03, 5, 0.25, 0.2);
    //testDrive(0.8, 0.03, 0.5, 0.5, 0.4);
    
    //driveChassis.wheelTest(0.5, 50);
    //hi
    /*
    driveChassis.moveForward(30);
    telemetry.addLine("moving forward");
    telemetry.update();
    sleep(1500);
    driveChassis.moveBackward(30);
    telemetry.addLine("moving backward");
    telemetry.update();
    sleep(1500);
    driveChassis.strafeLeft(40);
    telemetry.addLine("strafing left");
    telemetry.update();
    sleep(1500);
    driveChassis.strafeRight(40);
    telemetry.addLine("strafing right");
    telemetry.update();
    */
  }
  
  public void testTurn(double power, double gain, double accel, double tolerance, double deadband)
  {
    SimplifiedOdometryRobotInches.YAW_ACCEL = accel;
    SimplifiedOdometryRobotInches.YAW_TOLERANCE = tolerance;
    SimplifiedOdometryRobotInches.YAW_DEADBAND = deadband;
    SimplifiedOdometryRobotInches.YAW_GAIN = gain;
    
    driveChassis = new SimplifiedOdometryRobotInches(this);
    driveChassis.initialize(false);
    driveChassis.resetHeading();
    telemetry.addData("power: ", power);
    telemetry.addData("gain: ", gain);
    telemetry.addData("accel: ", accel);
    telemetry.addData("tolerance: ", tolerance);
    telemetry.addData("deadband: ", deadband);
    telemetry.update();
    runTimer.reset();
    driveChassis.turnTo(90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.turnTo(0, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.turnTo(90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.turnTo(0, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.turnTo(90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.turnTo(0, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.turnTo(90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.turnTo(0, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    telemetry.addData("runtime: ", runTimer.seconds() / 8);
    telemetry.update();
    sleep(5000);
  }
  
  public void testStrafe(double power, double gain, double accel, double tolerance, double deadband)
  {
    SimplifiedOdometryRobotInches.STRAFE_ACCEL = accel;
    SimplifiedOdometryRobotInches.STRAFE_TOLERANCE = tolerance;
    SimplifiedOdometryRobotInches.STRAFE_DEADBAND = deadband;
    SimplifiedOdometryRobotInches.STRAFE_GAIN = gain;
    driveChassis = new SimplifiedOdometryRobotInches(this);
    driveChassis.initialize(false);
    driveChassis.resetHeading();
    telemetry.addData("power: ", power);
    telemetry.addData("gain: ", gain);
    telemetry.addData("accel: ", accel);
    telemetry.addData("tolerance: ", tolerance);
    telemetry.addData("deadband: ", deadband);
    telemetry.update();
    runTimer.reset();
    driveChassis.strafe(95, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.strafe(-95, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.strafe(95, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.strafe(-95, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.strafe(95, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.strafe(-95, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.strafe(95, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.strafe(-95, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.strafe(95, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.strafe(-95, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    telemetry.addData("runtime: ", runTimer.seconds() / 10);
    telemetry.update();
    sleep(5000);
  }
  
  public void testDrive(double power, double gain, double accel, double tolerance, double deadband)
  {
    SimplifiedOdometryRobotInches.DRIVE_ACCEL = accel;
    SimplifiedOdometryRobotInches.DRIVE_TOLERANCE = tolerance;
    SimplifiedOdometryRobotInches.DRIVE_DEADBAND = deadband;
    SimplifiedOdometryRobotInches.DRIVE_GAIN = gain;
    driveChassis = new SimplifiedOdometryRobotInches(this);
    driveChassis.initialize(false);
    driveChassis.resetHeading();
    telemetry.addData("power: ", power);
    telemetry.addData("gain: ", gain);
    telemetry.addData("accel: ", accel);
    telemetry.addData("tolerance: ", tolerance);
    telemetry.addData("deadband: ", deadband);
    telemetry.update();
    runTimer.reset();
    driveChassis.drive(90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.drive(-90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.drive(90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.drive(-90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.drive(90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.drive(-90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.drive(90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.drive(-90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.drive(90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    driveChassis.drive(-90, power, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
    telemetry.addData("runtime: ", runTimer.seconds() / 10);
    telemetry.update();
    sleep(5000);
  }
  
  public void extensionTest(int extension)
  {
    telemetry.addData("current extension", extension);
    telemetry.update();
    arm.extendTo(extension);
    arm.retractAuto();
    sleep(5000);
  }
}

