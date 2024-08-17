package org.firstinspires.ftc.teamcode;

// tICKs per centimeter = 17.7914

public abstract class AutonomousRedBack extends Autonomous
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
    telemetry.addLine("PROP DETECTION: Its no left or right so its forward");
    telemetry.update();
    driveChassis.moveBackward(8);
    driveChassis.turnRight();
    driveChassis.straighten(backboardDirection());
    goBackboard(105);
    driveChassis.straighten(backboardDirection());
    goAwayFromRightWall(65 + yellowOffset(), 68);
    driveChassis.straighten(backboardDirection());
    pixelDropperYellow.drop_pixel();
    driveChassis.strafeLeft(75);
    driveChassis.moveForward(20);
  }
  
  public void goRight()
  {
    telemetry.addLine("PROP DETECTION: Right");
    telemetry.update();
    driveChassis.moveBackward(11);
    driveChassis.strafeRight(60);
    driveChassis.moveForward(60);
    driveChassis.straighten(backboardDirection());
    goAwayFromRightWall(55 + yellowOffset(), 79);
    driveChassis.straighten(backboardDirection());
    goBackboard(72);
    driveChassis.straighten(backboardDirection());
    pixelDropperYellow.drop_pixel();
    driveChassis.strafeLeft(110);
    driveChassis.moveForward(20);
  }
  
  public void goLeft()
  {
    telemetry.addLine("PROP DETECTION: Lefte");
    telemetry.update();
    driveChassis.moveBackward(11);
    driveChassis.turnRight();
    driveChassis.turnRight();
    driveChassis.straighten(backboardDirection());
    goAwayFromRightWall(72.5 + yellowOffset(), 79); // used to be 87 .5 from right wall
    driveChassis.straighten(backboardDirection());
    goBackboard(100);
    driveChassis.straighten(backboardDirection());
    pixelDropperYellow.drop_pixel();
    driveChassis.strafeLeft(72.5);//used to be 78.5
    driveChassis.moveForward(20);
  }
  
  /* remeber to make coments u use funny line = / and capital 8 = * */
}
//ooh eee ooh ahh ahh, ching chang, walla walla bing bang...
//ooh eee ooh ahh ahh, ching chang, walla walla bing bang...
//ooh eee ooh ahh ahh, ching chang, walla walla bing bang...