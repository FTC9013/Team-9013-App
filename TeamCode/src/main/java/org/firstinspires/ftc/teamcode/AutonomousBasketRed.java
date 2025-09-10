package org.firstinspires.ftc.teamcode;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Tripple Basket Red")

public class AutonomousBasketRed extends OdonomentryIntoTheDebt
{
  @Override
  public void runAuto()
  {
    arm.startExtendingTo(INITIAL_EXTENSION, 1);
    arm.resetAuto();
    sleep(100);
    arm.retractAuto();
    tripleSample();
  }
  
  @Override
  public void turnColor()
  {
    blang.turnRed();
  }
}