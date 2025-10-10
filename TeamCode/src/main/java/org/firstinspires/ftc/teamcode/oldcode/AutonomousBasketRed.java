package org.firstinspires.ftc.teamcode.oldcode;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Tripple Basket Red")

public class AutonomousBasketRed extends AutonomousParqueBlue.OdonomentryIntoTheDebt
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