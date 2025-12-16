package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red 1")

public class RedAutoBackOne extends DacodAuto
{
  public boolean amIBlue()
  {
    return false;
  }
  
  public boolean amIFirst()
  {
    return true;
  }
}
