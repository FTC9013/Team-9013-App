package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Intoh teh Depp")
// ticks per centemeter = 17.7914
public class IntoTheDepp extends LinearOpMode
{
  //public Blang blang;
  public MecanumDriveChassis driveChassis;
  public DistanceSensors distanceSensors;
  public ArmControl arm;
  static final int RAISE_ARM = 3675;
  static final int HOOK_POSITION = 1850;
  static final int PUSH_HOOK_POSITION = 1575;
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
    
    initialize();
    hookSample();
    strafeToBasket();
    grabAndDropSample();
    parkBeforeBackWall();
    
  }
  
  
  public void goAwayFromLeftWall(double distRight)
  
  {
    double distTravel = distanceSensors.leftDistance() - distRight;
    driveChassis.strafeLeft(distTravel);
  }
  
  public void stopBeforeBackWall(double distBack)
  {
    double distTravel = distanceSensors.backDistance() - distBack;
    driveChassis.moveBackward(distTravel);
    telemetry.addData("Distance travel:", distTravel);
    telemetry.addData("back distance sensor", distanceSensors.backDistance());
    telemetry.update();
  }
  
  public void hookSample()
  {
    arm.moveArmTo(HOOK_POSITION);
    driveChassis.moveForward(27);
    arm.moveArmTo(PUSH_HOOK_POSITION);
    telemetry.addLine("Moving Forward: 25 cm");
    telemetry.update();
    //arm stuf heer
    driveChassis.moveBackward(17, 0.3);
    arm.openGripper();
    sleep(1000);
    telemetry.addLine("Moving Backward: 15 cm");
    telemetry.update();
  }
  
  public void strafeToBasket()
  {
    telemetry.addLine("Strafing left");
    telemetry.update();
    driveChassis.strafeLeft(60);
    driveChassis.straighten(0);
    driveChassis.strafeLeft(60);
    goAwayFromLeftWall(23);
    telemetry.addLine("Stopping before back wall");
    telemetry.update();
    stopBeforeBackWall(5);
  }
  
  public void initialize()
  {
    arm.reset();
    //arm.extend();
    arm.moveArmTo(MEDIUM_ARM);
  }
  
  public void grabAndDropSample()
  {
    arm.reset();
    arm.closeGripper();
    sleep(800);
    arm.moveArmTo(RAISE_ARM);
    driveChassis.straighten(0);
    driveChassis.turnLeft();
    driveChassis.straighten(115);
    driveChassis.strafeRight(30);
    driveChassis.moveForward(20);
    driveChassis.strafeLeft(25);
    arm.openGripper();
    sleep(800);
    telemetry.addLine("sempal dropped");
    telemetry.update();
  }
  
  public void parkBeforeBackWall()
  {
    driveChassis.turnRight();
    driveChassis.straighten(0);
    stopBeforeBackWall(60);
    driveChassis.straighten(-90);
    driveChassis.moveForward(230);
    driveChassis.turnLeft();
    stopBeforeBackWall(3);
    telemetry.addLine("Moved Forward");
    telemetry.update();
    arm.closeGripper();
  }
}
