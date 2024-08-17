package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
// ticks per centemeter = 17.7914
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Red Front PARK")
public class RedFrontPARK extends Autonomous
{
  @Override
  public double yellowOffset()
  {
    return 0;
  }
  
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
    telemetry.addLine("PROP DETECTION: Its no left or right so its foward");
    telemetry.update();
    driveChassis.strafeRight(9);
    driveChassis.moveBackward(propSensors.backDistance() - 1);
    driveChassis.strafeRight(200);
    driveChassis.turnRight();
    driveChassis.moveForward(40);
    pixelDropperYellow.drop_pixel();
  }
  
  public void goRight()
  {
    telemetry.addLine("PROP DETECTION: Righte");
    telemetry.update();
    driveChassis.strafeRight(6);
    driveChassis.turnLeft();
    driveChassis.strafeLeft(4);
    driveChassis.moveBackward(propSensors.backDistance() - 1);
    driveChassis.strafeRight(200);
    driveChassis.turnRight();
    driveChassis.moveForward(40);
    pixelDropperYellow.drop_pixel();
  }
  
  public void goLeft()
  {
    telemetry.addLine("PROP DETECTION: Lefte");
    telemetry.update();
    driveChassis.moveBackward(5);
    driveChassis.turnRight();
    driveChassis.moveBackward(propSensors.backDistance() - 1);
    driveChassis.strafeRight(200);
    driveChassis.turnRight();
    driveChassis.moveForward(40);
    pixelDropperYellow.drop_pixel();
  }
  
  
}
  