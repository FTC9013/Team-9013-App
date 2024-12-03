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
  static final int HOOK_POSITION = 2884;
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
    arm.moveArmTo(HOOK_POSITION);
    //arm.retract();
    sleep(500);
    arm.stop();
    arm.reset();
  }
  
  public void hookSample()
  {
    arm.moveArmTo(HOOK_POSITION);
    driveChassis.moveForward(63);
    arm.extendForTime(0.5);
    arm.openGripper();
    sleep(1000);
    stopBeforeBackWall(33);
    //armor stuffs hear
  }
  
  
  public void strafeToBasket()
  {
    telemetry.addLine("Strafing left");
    telemetry.update();
    driveChassis.strafeLeft(120);
    driveChassis.straighten(0);
    goAwayFromLeftWall(35);
    telemetry.addLine("Stopping before back wall");
    stopBeforeBackWall(28);
    telemetry.update();
    
  }
  
  public void grabAndDropSample()
  {
    
    arm.retract();
    arm.reset();
    arm.closeGripper();
    sleep(800);
    arm.raiseMax();
    driveChassis.turnLeft();
    driveChassis.moveForward(23);
    goAwayFromLeftWall(10.75);
    arm.openGripper();
    sleep(800);
    driveChassis.moveBackward(25);
    
    telemetry.addLine("sample dropped");
    telemetry.update();
  }
  
  public void parkBeforeBackWall()
  {
    driveChassis.strafeRight(20);
    driveChassis.straighten(0);
    driveChassis.straighten(-90);
    driveChassis.moveForward(245);
    driveChassis.turnLeft();
    driveChassis.moveBackward(25);
    telemetry.addLine("Moved Forward");
    telemetry.update();
    arm.closeGripper();
  }
  
  public void test()
  {
    driveChassis.strafeLeftTicks(1000);
  }
}


