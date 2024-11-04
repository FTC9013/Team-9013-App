package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Atoomolonis test")
// ticks per centemeter = 17.7914
public class IntoTheDepp extends LinearOpMode
{
  public Blang blang;
  public MecanumDriveChassis driveChassis;
  public DistanceSensors distanceSensors;
  public ArmControl arm;
  static final int RAISE_ARM = 50;
  static final int LOWER_ARM = 0;
  static final int MEDIUM_ARM = 25;
  private final ElapsedTime runtime = new ElapsedTime();
  //public ArmControl arm;
  int turnDistanceYaw = 900;
  IMU imu;
  
  
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
    runtime.reset();
    // run until the end of the match (driver presses STOP)
    arm.reset();
    
    //arm.extendArm();
    //arm.moveArmTo(RAISE_ARM);
    //arm.moveArmTo(MEDIUM_ARM);
    //hang spaceminn witth a noose
    /*driveChassis.moveForward(20);
    driveChassis.turnLeft();
    sleep(1000);
    driveChassis.moveForward(20);
    driveChassis.turnLeft();
    sleep(1000);
    driveChassis.moveForward(20);
    driveChassis.turnLeft();
    sleep(1000);
    driveChassis.moveForward(20);
    driveChassis.turnLeft();
    sleep(1000);
    driveChassis.strafeRight(20);
    sleep(1000);
    driveChassis.strafeLeft(20);
    sleep(100000);*/
    driveChassis.moveForward(20);
    driveChassis.straighten(135);
    sleep(10000);
    driveChassis.moveForward(15);
    telemetry.addLine("mov fowakrd: seeping 4 5000 melesekonds");
    telemetry.update();
    sleep(1000);
    //arm stuf heer
    //arm.toggleGripper();
    driveChassis.moveBackward(10);
    telemetry.addLine("mov bankwd: seeping 4 5000 melesekonds");
    telemetry.update();
    sleep(1000);
    driveChassis.strafeLeft(120);
    goAwayFromLeftWall(20);
    
    telemetry.addLine("starfing left: sweping fore 5000 milenisenkens");
    telemetry.update();
    sleep(1000);
    //arm.moveArmTo(LOWER_ARM);
    //arm.toggleGripper();
    //----------------------------------------------------------------------------------
    //Fistr grab
    //grabb sampil
    telemetry.addLine("stawping beefore da back wall: sleping 5000 mlesekents");
    telemetry.update();
    stopBeforeBackWall(10);
    
    //arm.moveArmTo(RAISE_ARM);
    driveChassis.turnLeft();
    driveChassis.straighten(135);
    telemetry.addLine("sempal dropped: sweeping 5000 melelelesekend");
    telemetry.update();
    sleep(1000);
    
    
    //arm.toggleGripper();
    //arm dropp sampel n baskit
    //-----------------------------------------------------------------------------------
    //2d grabb
    driveChassis.straighten(0);
    stopBeforeBackWall(35);
    //arm.moveArmTo(LOWER_ARM);
    //arm.toggleGripper();
    //grrab sampel
    stopBeforeBackWall(10);
    //arm.moveArmTo(RAISE_ARM);
    driveChassis.turnLeft();
    driveChassis.straighten(135);
    //arm.toggleGripper();
    //arm dropp sampel n baskit
    
    //3th grab
    driveChassis.straighten(0);
    stopBeforeBackWall(35);
    goAwayFromLeftWall(10);
    //grrab sampel
    //arm.moveArmTo(LOWER_ARM);
    //arm.toggleGripper();
    stopBeforeBackWall(10);
    //arm.moveArmTo(RAISE_ARM);
    driveChassis.turnLeft();
    driveChassis.straighten(135);
    //arm dropp sampel n baskit
    //----------------------------------------------------------------------------------
    //arm.toggleGripper();
    //go to see zone
    driveChassis.straighten(0);
    driveChassis.moveForward(20);
    driveChassis.strafeRight(260);
    driveChassis.turnLeft();
    stopBeforeBackWall(35);
    driveChassis.turnRight();
    driveChassis.moveBackward(20);
    
    telemetry.addLine("Moved Forward");
    telemetry.update();
    
    while (opModeIsActive())
    {
      
    }
    
    
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
}
