package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Blue Front")
public class BlueAutoBackOne extends DacodAuto
{
  public boolean amIBlue()
  {
    return true;
  }
  
  public boolean amIFront()
  {
    return true;
  }
}