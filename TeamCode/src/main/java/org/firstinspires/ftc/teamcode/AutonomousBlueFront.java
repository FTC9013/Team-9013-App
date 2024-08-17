package org.firstinspires.ftc.teamcode;

// ticks per centemeter = 17.7914

public abstract class AutonomousBlueFront extends Autonomous
{
  @Override
  public double backboardDirection()
  {
    return 90;
  }
  
  public void turnColor()
  {
    blang.turnBlue();
  }
  
  public void goCenter()
  {
    int strafeDist = 200;
    double wallDist = 0;
    driveChassis.moveForward(2);
    telemetry.addLine("PROP DETECTION: Its no left or right so its foward");
    telemetry.update();
    sleep(5000);
    driveChassis.strafeRight(9);
    
    driveChassis.moveBackward(propSensors.backDistance() - 5);
    wallDist = propSensors.backDistance();
    while (wallDist > 5)
    {
      driveChassis.moveBackward(wallDist - 5);
      wallDist = propSensors.backDistance();
    }
    while (strafeDist > 0)
    {
      if (strafeDist > 50)
      {
        driveChassis.strafeLeft(50);
        strafeDist -= 50;
        if (wallDist < propSensors.backDistance())
        {
          driveChassis.moveForward(wallDist - propSensors.backDistance());
        }
      } else
      {
        driveChassis.strafeLeft(strafeDist);
        strafeDist = 0;
        if (wallDist < propSensors.backDistance())
        {
          driveChassis.moveForward(wallDist - propSensors.backDistance());
        }
      }
      driveChassis.straighten(0);
    }
    driveChassis.moveForward(10);
    driveChassis.turnLeft();
    goAwayFromLeftWall(64 + yellowOffset()); //was 77
    driveChassis.straighten(backboardDirection());
    goBackboard(70);
    driveChassis.straighten(backboardDirection());
    pixelDropperYellow.drop_pixel();
  }
  
  public void goRight()
  {
    telemetry.addLine("PROP DETECTION: Right");
    telemetry.update();
    sleep(5000);
    driveChassis.moveBackward(3);
    driveChassis.strafeRight(6);
    driveChassis.turnLeft();
    driveChassis.strafeLeft(4);
    driveChassis.straighten(0);
    driveChassis.moveBackward(propSensors.backDistance() - 0.5);
    driveChassis.straighten(0);
    driveChassis.strafeLeft(200);
    driveChassis.straighten(0);
    driveChassis.turnLeft();
    driveChassis.straighten(backboardDirection());
    goAwayFromLeftWall(79 + yellowOffset()); //was 92.5
    driveChassis.straighten(backboardDirection());
    goBackboard(70);
    driveChassis.straighten(backboardDirection());
    pixelDropperYellow.drop_pixel();
  }
  
  public void goLeft()
  {
    telemetry.addLine("PROP DETECTION: Lefte");
    telemetry.update();
    sleep(5000);
    driveChassis.moveBackward(5);
    driveChassis.turnRight();
    driveChassis.straighten(0);
    driveChassis.moveBackward(propSensors.backDistance() - 0.5);
    driveChassis.straighten(0);
    driveChassis.strafeLeft(200);
    driveChassis.turnLeft();
    driveChassis.straighten(backboardDirection());
    goAwayFromLeftWall(52 + yellowOffset()); //was 55
    driveChassis.straighten(backboardDirection());
    goBackboard(70);
    driveChassis.straighten(backboardDirection());
    pixelDropperYellow.drop_pixel();
  }
}
  