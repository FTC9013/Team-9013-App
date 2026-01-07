package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;

public class PoseStorage
{
  //ending pose from auto
  public static Pose2d currentPose = new Pose2d(0, 0, 0);
  //pose from auto
  public static Pose2d launchPose = new Pose2d(0, 0, Math.toRadians(0));
}
