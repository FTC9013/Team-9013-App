package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "Red 2")
public class RedAutoBackTwo extends DacodAuto
{
  @Override
  public Pose2d getStartingPose()
  {
    return adjust(STARTING2);
  }
  
  public boolean amIBlue()
  {
    return false;
  }
}
