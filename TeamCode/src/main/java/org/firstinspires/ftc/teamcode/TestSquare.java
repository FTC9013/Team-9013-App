package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "go box")
// ticks per centepeder = 17.7914
public abstract class TestSquare extends LinearOpMode
{
  public MecanumDriveChassis driveChassis;
  
  @Override
  public void runOpMode()
  {
    driveChassis.moveForward(20);
    telemetry.addLine("mobing forward");
    telemetry.update();
    driveChassis.turnLeft();
    telemetry.addLine("Turnin left");
    telemetry.update();
    driveChassis.moveForward(20);
    telemetry.addLine("mobing forward");
    telemetry.update();
    driveChassis.turnLeft();
    telemetry.addLine("Turnin left");
    telemetry.update();
    driveChassis.moveForward(20);
    telemetry.addLine("mobing forward");
    telemetry.update();
    driveChassis.turnLeft();
    telemetry.addLine("Turnin left");
    telemetry.update();
    driveChassis.moveForward(20);
    telemetry.addLine("mobing forward");
    telemetry.update();
    driveChassis.turnLeft();
    telemetry.addLine("Turnin left");
    telemetry.update();
  }
}
  


