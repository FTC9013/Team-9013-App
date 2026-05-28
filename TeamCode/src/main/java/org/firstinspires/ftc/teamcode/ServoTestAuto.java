package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name = "change the name from blue front. servo test")
public class ServoTestAuto extends LinearOpMode
{
  public ServoTest servoMotor;
  
  public void runOpMode()
  {
    servoMotor = new ServoTest(hardwareMap, telemetry);
    waitForStart();
    while (opModeIsActive())
    {
      servoMotor.conveyForward();
      sleep(1000);
      servoMotor.conveyBackwardTwo();
      sleep(1000);
      servoMotor.stopConveying();
    }
  }
}