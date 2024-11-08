package org.firstinspires.ftc.teamcode;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "big points!")
public class AutonomousMain extends IntoTheDepp
{
  @Override
  public void runAuto()
  {
    initialize();
    hookSample();
    strafeToBasket();
    grabAndDropSample();
    parkBeforeBackWall();
    
  }
}
