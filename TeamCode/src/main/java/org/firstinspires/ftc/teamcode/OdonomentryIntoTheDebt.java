package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


// ticks per centepeder = 17.7914
public abstract class OdonomentryIntoTheDebt extends LinearOpMode
{
  public Blang blang;
  public SimplifiedOdometryRobotInches driveChassisOdom;
  public MecanumDriveChassis driveChassis;
  public DistanceSensors distanceSensors;
  public ArmControl arm;
  public DistanceSensors propSensors;
  static final double DEFAULT_POWER = 0.8;
  static final double DEFAULT_HOLD_TIME = 0;
  double tickPerCm = 20.24278;
  double maxSpeed = 0.6;
  double minSpeed = 0.1;
  double decelDistCm = 20;
  static final int RAISE_ARM = 3000;
  static final int HOOK_POSITION = 2800;
  static final int STARTING_POSITION = 2600;
  static final int DROP_POSITION = 3350;
  static final int MAX_EXTENSION = 2400;
  static final int MEDIUM_ARM = 2000;
  static final int INITIAL_EXTENSION = 2600;
  
  
  // a timer for the various automation activities.
  @Override
  public void runOpMode()
  {
    arm = new ArmControl(hardwareMap, telemetry);
    blang = new Blang(hardwareMap);
    distanceSensors = new DistanceSensors(hardwareMap, telemetry);
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    // setup a instance of our drive system
    // Declare OpMode members.
    driveChassisOdom = new SimplifiedOdometryRobotInches(this);
    driveChassisOdom.initialize(false);
    driveChassis = new MecanumDriveChassis(hardwareMap, telemetry);
    turnColor();
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
    driveChassisOdom.resetHeading();
    runAuto();
    //goFromLeftWall(30);
    //sleep(5000);
  }
  
  public abstract void runAuto();
  
  public abstract void turnColor();
  
  public void goAwayFromLeftWall(double distRight)
  {
    telemetry.addData("Left sensor:", distanceSensors.leftDistance());
    telemetry.update();
    double distTravel = distanceSensors.leftDistance() - distRight;
    strafe(distTravel);
    telemetry.addData("Left sensor:", distanceSensors.leftDistance());
    telemetry.update();
  }
  
  public void goAwayFromRightWall(double distLeft)
  {
    double distTravel = distanceSensors.rightDistance() - distLeft;
    strafe(-distTravel);
  }
  
  public void stopBeforeBackWall(double distBack)
  {
    double distTravel = distanceSensors.backDistance() - distBack;
    drive(-distTravel);
    telemetry.addData("Distance travel:", distTravel);
    telemetry.addData("back distance sensor", distanceSensors.backDistance());
    telemetry.update();
  }
  
  public void initialize()
  {
    arm.extendTo(INITIAL_EXTENSION);
    arm.resetAuto();
    
    arm.retract();
    sleep(200);
    arm.stop();
    telemetry.update();
    arm.stop();
    arm.startMovingTo(-1800);
    sleep(250);
  }
  
  public void hookSample()
  {
    //arm.moveArmTo(HOOK_POSITION);
    drive(64);
    telemetry.addLine("sigma sigma boy");
    //arm.extendForTime(0.4);
    arm.openGripper();
    arm.moveArmTo(-1500);
    //sleep(500);
    arm.retract();
    sleep(200);
    arm.stop();
    drive(-37);
  }
  
  
  public void strafeToBasket()
  {
    telemetry.addLine("Strafing left");
    telemetry.update();
    arm.startMovingTo(-1400);
    strafe(89);
  }
  
  public void grabAndDropSample()
  {
    arm.reset();
    arm.moveArmTo(100);
    arm.closeGripper();
    sleep(800);
    arm.startMovingTo(4300);
    arm.extendTo(1700);
    telemetry.addLine("Turning");
    telemetry.update();
    turn(90);
    telemetry.addLine("Strafing");
    telemetry.update();
    drive(46);
    strafe(21);
    arm.extendTo(750);
    arm.moveArmTo(3700);
    arm.openGripper();
    arm.moveArmTo(4300);
    //ching billing
    telemetry.addLine("sample dropped");
    telemetry.update();
  }
  
  public void touchBar()
  {
    
    drive(-60);
    arm.retract();
    sleep(250);
    arm.stop();
    turn(0);
    
    arm.startMovingTo(STARTING_POSITION - 350);
    drive(95);
    turn(-90);
    
    drive(distanceSensors.frontDistance() - 2);
    
    
  }
  
  public void drive(double distanceCm)
  {
    driveChassisOdom.drive(distanceCm, DEFAULT_POWER, DEFAULT_HOLD_TIME);
  }
  
  public void strafe(double distanceCm)
  {
    driveChassisOdom.strafe(distanceCm, DEFAULT_POWER, DEFAULT_HOLD_TIME);
  }
  
  public void turn(double degree)
  {
    driveChassisOdom.turnTo(degree, DEFAULT_POWER, DEFAULT_HOLD_TIME);
  }
}


