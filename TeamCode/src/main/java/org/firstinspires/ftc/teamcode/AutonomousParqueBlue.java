package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "parque blue")
public class AutonomousParqueBlue extends AutonomousParque
{
  @Override
  public void turnColor()
  {
    blang.turnBlue();
  }
}
