package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Into the Deep")
// ticks per centemeter = 17.7914
public class IntoTheDepp extends LinearOpMode
{
  //public Blang blang;
  public MecanumDriveChassis driveChassis;
  public DistanceSensors distanceSensors;
  public ArmControl arm;
  static final int RAISE_ARM = 50;
  static final int LOWER_ARM = 0;
  static final int MEDIUM_ARM = 25;
  
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
    driveChassis.moveForward(15);
    telemetry.addLine("Moving Forward: 15 cm");
    telemetry.update();
    sleep(1000);
    //arm stuf heer
    //arm.toggleGripper();
    driveChassis.moveBackward(10);
    telemetry.addLine("Moving Backward: 10 cm");
    telemetry.update();
    sleep(1000);
  }
  
  public void strafeToBasket()
  {
    telemetry.addLine("Strafing left");
    telemetry.update();
    driveChassis.strafeLeft(60);
    sleep(200);
    driveChassis.straighten(0);
    sleep(200);
    driveChassis.strafeLeft(60);
    goAwayFromLeftWall(20);
    sleep(1000);
    telemetry.addLine("Stopping before back wall");
    telemetry.update();
    stopBeforeBackWall(20);
  }
  
  public void initialize()
  {
    arm.reset();
    arm.extend();
    arm.moveArmTo(MEDIUM_ARM);
  }
  
  public void grabAndDropSample()
  {
    arm.moveArmTo(LOWER_ARM);
    arm.toggleGripper();
    arm.moveArmTo(RAISE_ARM);
    sleep(200);
    driveChassis.straighten(0);
    sleep(100);
    driveChassis.turnLeft();
    driveChassis.straighten(135);
    arm.toggleGripper();
    telemetry.addLine("sempal dropped");
    telemetry.update();
    sleep(1000);
  }
  
  public void parkBeforeBackWall()
  {
    driveChassis.turnRight();
    driveChassis.straighten(0);
    stopBeforeBackWall(65);
    driveChassis.straighten(-90);
    sleep(100);
    driveChassis.moveForward(100);
    sleep(250);
    driveChassis.straighten(-90);
    driveChassis.moveForward(115);
    driveChassis.turnLeft();
    stopBeforeBackWall(3);
    telemetry.addLine("Moved Forward");
    telemetry.update();
  }
}
