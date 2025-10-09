package org.firstinspires.ftc.teamcode.oldcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp(name = "blangTest :)", group = "Robot")
// ticks per centepeder = 17.7914
public class BlangTest extends LinearOpMode
{
  public Blang blang;
  
  // a timer for the various automation activities.
  @Override
  public void runOpMode()
  {
    blang = new Blang(hardwareMap);
  }
}
