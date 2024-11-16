package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


// ticks per centepeder = 17.7914
public abstract class IntoTheDebt extends LinearOpMode
{
  //public Blang blang;
  public MecanumDriveChassis driveChassis;
  public DistanceSensors distanceSensors;
  public ArmControl arm;
  static final int RAISE_ARM = 3675;
  static final int HOOK_POSITION = 2450;
  static final int PUSH_HOOK_POSITION = 2700;
  static final int MAX_EXTENSION = 2400;
  static final int MEDIUM_ARM = 2000;
  
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
    double distTravel = distanceSensors.leftDistance() - distRight;
    driveChassis.strafeLeft(distTravel);
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
    arm.reset();
    arm.moveArmTo(HOOK_POSITION);
  }
  
  public void hookSample()
  {
    driveChassis.moveForward(48);
    //arm.extend(MAX_EXTENSION);
    arm.openGripper();
    sleep(1000);
    arm.moveArmTo(RAISE_ARM);
    arm.releaseBrake();
    telemetry.addLine("Moving Forward: 27 cm");
    telemetry.update();
    //armor stuffs hear
  }
  
  
  public void strafeToBasket()
  {
    telemetry.addLine("Strafing left");
    telemetry.update();
    driveChassis.strafeLeft(120);
    driveChassis.straighten(0);
    goAwayFromLeftWall(31.5);
    telemetry.addLine("Stopping before back wall");
    telemetry.update();
    stopBeforeBackWall(33);
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
    //extend 1483cm extension motor
    arm.extendMax();
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
    stopBeforeBackWall(60);
    driveChassis.straighten(-90);
    driveChassis.moveForward(245);
    driveChassis.turnLeft();
    driveChassis.moveBackward(25);
    telemetry.addLine("Moved Forward");
    telemetry.update();
    arm.closeGripper();
  }
}
