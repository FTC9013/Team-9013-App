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
  double maxSpeed = 0.6;
  double minSpeed = 0.1;
  double decelDistCm = 20;
  static final int RAISE_ARM = 3000;
  static final int HOOK_POSITION = 2800;
  static final int STARTING_POSITION = 3000;
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
    //goFromLeftWall(30);
    //sleep(5000);
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
    arm.resetTelop();
    arm.extendTo(INITIAL_EXTENSION);
    arm.retract();
    arm.moveArmTo(1000);
    arm.stop();
    arm.moveArmTo(0);
  }
  
  public void hookSample()
  {
    //arm.moveArmTo(HOOK_POSITION);
    driveChassis.moveForward(61);
    arm.extendForTime(0.14);
    arm.openGripper();
    arm.moveArmTo(130);
    sleep(500);
    arm.retract();
    sleep(100);
    arm.stop();
    driveChassis.moveBackward(25);
    //armor stuffs hear
  }
  
  
  public void strafeToBasket()
  {
    telemetry.addLine("Strafing left");
    telemetry.update();
    driveChassis.strafeLeft(90);
    driveChassis.straighten(0);
    goFromLeftWall(35);
    driveChassis.straighten(0);
    telemetry.addLine("Stopping before back wall");
    goFromBackWall(43);
    //goFromLeftWall(38.5);
    //goFromBackWall(42);
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
    driveChassis.moveForward(distanceSensors.frontDistance() - 5);
    goAwayFromLeftWall(12);
    arm.openGripper();
    sleep(1000);
    arm.raise();
    arm.extendForTime(0.5);
    driveChassis.moveBackward(5);
    
    arm.retract();
    arm.stop();
    
    telemetry.addLine("sample dropped");
    telemetry.update();
  }
  
  public void touchBar()
  {
    driveChassis.moveBackward(30);
    driveChassis.turnRight();
    driveChassis.straighten(0);
    driveChassis.moveForward(95);
    driveChassis.straighten(0);
    driveChassis.turnRight();
    driveChassis.moveForward(distanceSensors.frontDistance());
    arm.moveArmTo(STARTING_POSITION);
    
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
  
  public void goFromLeftWall(double distFromLeftWell)
  
  {
    telemetry.addData("Left sensor:", distanceSensors.leftDistance());
    telemetry.update();
    double distTravel = distanceSensors.leftDistance() - distFromLeftWell;
    driveChassis.startStrafingLefte(maxSpeed);
    
    while (distanceSensors.leftDistance() > distFromLeftWell && opModeIsActive())
    {
      telemetry.addData("Left sensor:", distanceSensors.leftDistance());
      double distAwayTar = distanceSensors.leftDistance() - distFromLeftWell;
      double desiredSpeed = (maxSpeed - minSpeed) / decelDistCm * distAwayTar + minSpeed;
      if (desiredSpeed >= maxSpeed)
      {
        desiredSpeed = maxSpeed;
      }
      telemetry.addData("desired sped:", desiredSpeed);
      telemetry.update();
      driveChassis.startStrafingLefte(desiredSpeed);
    }
    driveChassis.stop_motors();
    sleep(500);
  }
  
  public void goFromBackWall(double distFromBackWall)
  
  {
    telemetry.addData("Back sensor:", distanceSensors.backDistance());
    telemetry.update();
    driveChassis.startMovingBackward(0.55);
    while (distanceSensors.backDistance() > distFromBackWall && opModeIsActive())
    {
      telemetry.addData("Back sensor:", distanceSensors.backDistance());
      telemetry.update();
    }
    driveChassis.stop_motors();
    sleep(500);
  }
  
  public void test()
  {
    driveChassis.strafeLeft(30);
  }
}


