package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Test Auto")

public class ArmTest extends LinearOpMode
{
  public MecanumDriveChassis driveChassis;
  
  @Override
  
  public void runOpMode()
  {
    driveChassis = new MecanumDriveChassis(hardwareMap, telemetry);
    waitForStart();
    //driveChassis.wheelTest(0.5, 50);
    //hi
    waitForStart();
    driveChassis.moveForward(30);
    telemetry.addLine("moving forward");
    telemetry.update();
    driveChassis.moveBackward(20);
    telemetry.addLine("moving backward");
    telemetry.update();
    driveChassis.turnRight();
    telemetry.addLine("Turning right");
    driveChassis.turnLeft();
    telemetry.addLine("Turning left");
    telemetry.update();
    /*
    driveChassis.moveForward(30);
    telemetry.addLine("mobing forward");
    telemetry.update();
    sleep(1500);
    driveChassis.moveBackward(30);
    telemetry.addLine("mobing backword");
    telemetry.update();
    sleep(1500);
    driveChassis.strafeLeft(40);
    telemetry.addLine("strabing left");
    telemetry.update();
    sleep(1500);
    driveChassis.strafeRight(40);
    telemetry.addLine("strabing right");
    telemetry.update();
    */
  }
}