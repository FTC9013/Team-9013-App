package org.firstinspires.ftc.teamcode.oldcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "parque red")
public class AutonomousParqueRed extends AutonomousParque
{
  @Override
  public void turnColor()
  {
    blang.turnRed();
  }
}
