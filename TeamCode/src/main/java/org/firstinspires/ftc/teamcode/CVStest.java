package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "CVStest", group = "Linear Opmode")

public abstract class CVStest extends LinearOpMode
{
  private ConceptVisionColorSensor conceptVisionColorSensor;
  
  
  @Override
  public void runOpMode()
  {
    while (opModeIsActive())
    {
      conceptVisionColorSensor.colorSensing();
    }
  }
  
}
