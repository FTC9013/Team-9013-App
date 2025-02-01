package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Super seven")

public class AutonomousBasket extends IntoTheDebt
{
  @Override
  public void runAuto()
  {
    initialize();
    sleep(5000);
    //test();
    strafeToBasket();
    grabAndDropSample();
    touchBar();
    //parkBeforeBackWall();
  }
  
  @Override
  public void turnColor()
  {
    blang.turnBlue();
  }
}