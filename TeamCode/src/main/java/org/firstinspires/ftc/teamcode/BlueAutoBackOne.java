package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue 1")
public class BlueAutoBackOne extends DacodAuto
{
  @Override
  public Pose2d getStartingPose()
  {
    return adjust(STARTING1);
  }
  
  public boolean amIBlue()
  {
    return true;
  }
}
