package org.firstinspires.ftc.teamcode;

public abstract class AutonomousMain extends IntoTheDebt
{
  @Override
  public void runAuto()
  {
    initialize();
    //test();
    hookSample();
    strafeToBasket();
    grabAndDropSample();
    touchBar();
    //parkBeforeBackWall();
    
  }
  
}