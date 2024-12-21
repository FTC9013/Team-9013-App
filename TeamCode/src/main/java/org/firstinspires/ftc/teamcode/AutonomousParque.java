package org.firstinspires.ftc.teamcode;


public abstract class AutonomousParque extends IntoTheDebt
{
  @Override
  public void runAuto()
  {
    sleep(10000);
    initialize();
    hookSample();
    arm.moveArmTo(RAISE_ARM);
    driveChassis.strafeRight(100);
    goAwayFromRightWall(10);
    stopBeforeBackWall(5);
    
  }
}
