package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


// ticks per centepeder = 17.7914
public abstract class IntoTheDebt extends LinearOpMode
{
  //public Blang blang;
  public MecanumDriveChassis driveChassis;
  public DistanceSensors distanceSensors;
  public ArmControl arm;
  public DistanceSensors propSensors;
  double tickPerCm = 20.24278;
  static final int RAISE_ARM = 3000;
  static final int HOOK_POSITION = 2800;
  static final int DROP_POSITION = 3350;
  static final int MAX_EXTENSION = 2400;
  static final int MEDIUM_ARM = 2000;
  static final int INITIAL_EXTENSION = 3000;
  
  
  // a timer for the various automation activities.
  @Override
  public void runOpMode()
  {
    arm = new ArmControl(hardwareMap, telemetry);
    //blang = new Blang(hardwareMap, telemetry);
    distanceSensors = new DistanceSensors(hardwareMap, telemetry);
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    // setup a instance of our drive system
    // Declare OpMode members.
    driveChassis = new MecanumDriveChassis(hardwareMap, telemetry);
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
    runAuto();
    
  }
  
  public abstract void runAuto();
  
  public void goAwayFromLeftWall(double distRight)
  
  {
    telemetry.addData("Left sensor:", distanceSensors.leftDistance());
    telemetry.update();
    double distTravel = distanceSensors.leftDistance() - distRight;
    driveChassis.strafeLeft(distTravel);
    telemetry.addData("Left sensor:", distanceSensors.leftDistance());
    telemetry.update();
  }
  
  public void goAwayFromRightWall(double distLeft)
  
  {
    double distTravel = distanceSensors.rightDistance() - distLeft;
    driveChassis.strafeRight(distTravel);
  }
  
  public void stopBeforeBackWall(double distBack)
  {
    double distTravel = distanceSensors.backDistance() - distBack;
    driveChassis.moveBackward(distTravel);
    telemetry.addData("Distance travel:", distTravel);
    telemetry.addData("back distance sensor", distanceSensors.backDistance());
    telemetry.update();
  }
  
  public void initialize()
  {
    arm.extendTo(INITIAL_EXTENSION);
    // DOC NOT USE arm.moveArmTo(HOOK_POSITION);
    arm.retract();
    arm.moveArmTo(1000);
    arm.stop();
    arm.reset();
  }
  
  public void hookSample()
  {
    arm.moveArmTo(HOOK_POSITION);
    driveChassis.moveForward(61);
    arm.extendForTime(0.14);
    arm.openGripper();
    arm.moveArmTo(HOOK_POSITION + 125);
    sleep(1000);
    arm.retract();
    sleep(500);
    arm.stop();
    driveChassis.moveBackward(25);
    //armor stuffs hear
  }
  
  
  public void strafeToBasket()
  {
    telemetry.addLine("Strafing left");
    telemetry.update();
    driveChassis.strafeLeft(120);
    driveChassis.straighten(0);
    goAwayFromLeftWall(30);
    telemetry.addLine("Stopping before back wall");
    stopBeforeBackWall(34);
    goAwayFromLeftWall(30);
    stopBeforeBackWall(34);
    driveChassis.straighten(0);
    telemetry.update();
  }
  
  public void grabAndDropSample()
  {
    
    
    arm.reset();
    arm.moveArmTo(100);
    arm.closeGripper();
    sleep(800);
    arm.moveArmTo(4000);
    arm.extendForTime(2);
    driveChassis.turnLeft();
    driveChassis.moveForward(24);
    goAwayFromLeftWall(13);
    arm.openGripper();
    sleep(1000);
    arm.raise();
    arm.extendForTime(0.5);
    arm.retract();
    arm.stop();
    driveChassis.moveBackward(25);
    
    telemetry.addLine("sample dropped");
    telemetry.update();
  }
  
  public void slamIntoWallNotTooHard()
  {
    
    arm.reset();
    arm.moveArmTo(100);
    arm.closeGripper();
    sleep(800);
    arm.moveArmTo(4000);
    driveChassis.turnLeft();
    driveChassis.startMovingForward(propSensors.frontDistance() + 1, 0.1);
    goAwayFromLeftWall(13);
    arm.extendForTime(2);
    arm.openGripper();
    sleep(100);
    arm.retract();
    arm.stop();
    sleep(800);
    driveChassis.moveBackward(25);
    
    telemetry.addLine("sample dropped");
    telemetry.update();
  }
  
  public void parkBeforeBackWall()
  {
    driveChassis.strafeRight(50);
    driveChassis.turnRight();
    driveChassis.turnRight();
    driveChassis.straighten(90);
    driveChassis.moveForward(210);
    driveChassis.turnLeft();
    driveChassis.moveBackward(35);
    telemetry.addLine("Moved Forward");
    telemetry.update();
    arm.closeGripper();
  }
  
  public void test()
  {
    driveChassis.strafeLeft(30);
  }
}


