package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Main blue")
public class AutonomousMainBlue extends AutonomousMain
{
  @Override
  public void turnColor()
  {
    blang.turnBlue();
  }
}
