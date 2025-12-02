package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Blue 2")
public class BlueAutoBackTwo extends DacodAuto
{
  @Override
  public Pose2d getStartingPose()
  {
    return adjust(STARTING2);
  }
  
  @Override
  public boolean amIBlue()
  {
    return true;
  }
}