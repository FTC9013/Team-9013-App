package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


// ticks per centepeder = 17.7914
public abstract class IntoTheDebt extends LinearOpMode
{
  //public Blang blang;
  public MecanumDriveChassis driveChassis;
  public DistanceSensors distanceSensors;
  public ArmControl arm;
  double tickPerCm = 20.24278;
  static final int RAISE_ARM = 3000;
  static final int HOOK_POSITION = 2750;
  static final int DROP_POSITION = 2600;
  static final int PUSH_HOOK_POSITION = 2700;
  static final int MAX_EXTENSION = 2400;
  static final int MEDIUM_ARM = 2000;
  static final int INITIAL_EXTENSION = 2850;
  
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
    //arm.extendTo(INITIAL_EXTENSION);
    // DOC NOT USE arm.moveArmTo(HOOK_POSITION);
    //arm.retract();
    //sleep(500);
    arm.stop();
    arm.reset();
  }
  
  public void hookSample()
  {
    arm.moveArmTo(HOOK_POSITION);
    driveChassis.moveForward(61);
    arm.extendForTime(0.35);
    arm.openGripper();
    sleep(1000);
    arm.retract();
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
    stopBeforeBackWall(27);
    telemetry.update();
    
  }
  
  public void grabAndDropSample()
  {
    
    
    arm.reset();
    arm.closeGripper();
    sleep(800);
    arm.raiseMax();
    driveChassis.turnLeft();
    driveChassis.moveForward(24);
    goAwayFromLeftWall(13);
    arm.extendTo(1600);
    arm.moveArmTo(DROP_POSITION);
    arm.openGripper();
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
    driveChassis.moveForward(210);
    driveChassis.turnLeft();
    driveChassis.moveBackward(25);
    telemetry.addLine("Moved Forward");
    telemetry.update();
    arm.closeGripper();
  }
  
  public void test()
  {
    driveChassis.strafeLeft(30);
  }
}


