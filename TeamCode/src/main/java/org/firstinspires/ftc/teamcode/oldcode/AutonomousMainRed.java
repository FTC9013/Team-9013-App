package org.firstinspires.ftc.teamcode.oldcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Main red")
public class AutonomousMainRed extends AutonomousMain
{
  @Override
  public void turnColor()
  {
    blang.turnRed();
  }
}
