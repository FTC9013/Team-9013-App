package org.firstinspires.ftc.teamcode.OldRobotsParts;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Front")

public class RedAutoBackOne extends DacodAuto
{
  public boolean amIBlue()
  {
    return false;
  }
  
  public boolean amIFront()
  {
    return true;
  }
}
