package org.firstinspires.ftc.teamcode;

// ticks per centemeter = 17.7914

public abstract class AutonomousRedFront extends Autonomous
{
  @Override
  public double backboardDirection()
  {
    return -90;
  }
  
  public void turnColor()
  {
    blang.turnRed();
  }
  
  public void goCenter()
  {
    sleep(5000);
    telemetry.addLine("PROP DETECTION: Its no left or right so its foward");
    telemetry.update();
    driveChassis.strafeRight(9);
    driveChassis.moveBackward(propSensors.backDistance() - 1);
    driveChassis.strafeRight(200);
    driveChassis.turnRight();
    goAwayFromRightWall(65 + yellowOffset(), 4); //was 70
    goBackboard(70);
    pixelDropperYellow.drop_pixel();
  }
  
  public void goRight()
  {
    sleep(5000);
    telemetry.addLine("PROP DETECTION: Righte");
    telemetry.update();
    driveChassis.moveBackward(3);
    driveChassis.strafeRight(6);
    driveChassis.turnLeft();
    driveChassis.strafeLeft(4);
    driveChassis.moveBackward(propSensors.backDistance() - 1);
    driveChassis.strafeRight(200);
    driveChassis.turnRight();
    goAwayFromRightWall(55 + yellowOffset(), 4); //was 50
    goBackboard(70);
    pixelDropperYellow.drop_pixel();
    
  }
  
  public void goLeft()
  {
    sleep(5000);
    telemetry.addLine("PROP DETECTION: Lefte");
    telemetry.update();
    driveChassis.moveBackward(5);
    driveChassis.turnRight();
    driveChassis.moveBackward(propSensors.backDistance() - 1);
    driveChassis.strafeRight(200);
    driveChassis.turnRight();
    goAwayFromRightWall(82.5 + yellowOffset(), 4); //was 85.5
    goBackboard(70);
    pixelDropperYellow.drop_pixel();
  }
  
  
}
  