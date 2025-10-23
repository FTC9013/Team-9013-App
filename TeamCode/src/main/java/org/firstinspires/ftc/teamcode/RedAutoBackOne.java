package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red 1")
public class RedAutoBackOne extends DacodAuto
{
  @Override
  public Pose2d getStartingPose()
  {
    return adjust(STARTING1);
  }
  
  public boolean amIBlue()
  {
    return false;
  }
}
