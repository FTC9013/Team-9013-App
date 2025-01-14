package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


// ticks per centepeder = 17.7914
public abstract class OdonomentryIntoTheDebt extends LinearOpMode
{
  public Blang blang;
  public SimplifiedOdometryRobot driveChassis;
  public DistanceSensors distanceSensors;
  public ArmControl arm;
  public DistanceSensors propSensors;
  static final double DEFAULT_POWER = 0.5;
  static final double DEFAULT_HOLD_TIME = 0.05;
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
    driveChassis = new SimplifiedOdometryRobot(this);
    turnColor();
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
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
    driveChassis.strafe(distTravel, DEFAULT_POWER, DEFAULT_HOLD_TIME);
    telemetry.addData("Left sensor:", distanceSensors.leftDistance());
    telemetry.update();
  }
  
  public void goAwayFromRightWall(double distLeft)
  
  {
    double distTravel = distanceSensors.rightDistance() - distLeft;
    driveChassis.strafe(-distTravel, DEFAULT_POWER, DEFAULT_HOLD_TIME);
  }
  
  public void stopBeforeBackWall(double distBack)
  {
    double distTravel = distanceSensors.backDistance() - distBack;
    driveChassis.drive(-distTravel, DEFAULT_POWER, DEFAULT_HOLD_TIME);
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
    //√∙·
    telemetry.update();
    arm.stop();
    arm.moveArmTo(-1600);
  }
  
  public void hookSample()
  {
    //arm.moveArmTo(HOOK_POSITION);
    driveChassis.drive(56, DEFAULT_POWER, DEFAULT_HOLD_TIME);
    
    arm.extendForTime(0.4);
    arm.openGripper();
    arm.moveArmTo(-1500);
    //sleep(500);
    arm.retract();
    sleep(200);
    arm.stop();
    driveChassis.drive(-25, DEFAULT_POWER, DEFAULT_HOLD_TIME);
    //armor stuffs hear
  }
  
  
  public void strafeToBasket()
  {
    telemetry.addLine("Strafing left");
    telemetry.update();
    driveChassis.strafe(90, DEFAULT_POWER, DEFAULT_HOLD_TIME);
    goAwayFromLeftWall(37);
    telemetry.addLine("Stopping before back wall");
    stopBeforeBackWall(29);
    goAwayFromLeftWall(37);
    stopBeforeBackWall(29);
    telemetry.update();
  }
  
  public void grabAndDropSample()
  {
    arm.reset();
    arm.moveArmTo(100);
    arm.closeGripper();
    sleep(800);
    arm.moveArmTo(4300);
    arm.extendForTime(1.5);
    driveChassis.turnTo(90, DEFAULT_POWER, DEFAULT_HOLD_TIME);
    goAwayFromLeftWall(15);
    driveChassis.drive(distanceSensors.frontDistance() - 5, DEFAULT_POWER, DEFAULT_HOLD_TIME);
    sleep(750);
    arm.moveArmTo(3700);
    arm.openGripper();
    arm.extendForTime(0.7654321);
    arm.moveArmTo(4300);
    
    telemetry.addLine("sample dropped");
    telemetry.update();
  }
  
  public void touchBar()
  {
    
    driveChassis.drive(-60, DEFAULT_POWER, DEFAULT_HOLD_TIME);
    arm.retract();
    sleep(250);
    arm.stop();
    driveChassis.turnTo(0, DEFAULT_POWER, DEFAULT_HOLD_TIME);
    
    arm.startMovingTo(STARTING_POSITION - 350);
    driveChassis.drive(95, DEFAULT_POWER, DEFAULT_HOLD_TIME);
    driveChassis.turnTo(-90, DEFAULT_POWER, DEFAULT_HOLD_TIME);
    
    driveChassis.drive(distanceSensors.frontDistance() - 2, DEFAULT_POWER, DEFAULT_HOLD_TIME);
    
    
  }
  
  
}


