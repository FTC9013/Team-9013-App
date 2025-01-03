package org.firstinspires.ftc.teamcode;


public abstract class AutonomousParque extends IntoTheDebt
{
  @Override
  public void runAuto()
  {
    sleep(15000);
    initialize();
    hookSample();
    stopBeforeBackWall(5);
    arm.moveArmTo(-1600);
    driveChassis.strafeRight(50);
    driveChassis.straighten(0);
    driveChassis.strafeRight(50);
    goAwayFromRightWall(10);
    
    
  }
}
