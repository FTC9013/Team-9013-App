package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting
{
  public static void main(String[] args)
  {
    MeepMeep meepMeep = new MeepMeep(800);
    
    RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
      // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
      .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
      .build();
    
    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-61.25, 11.5, 0))
      .lineToX(-31.25)
      .splineToLinearHeading(new Pose2d(-40.25, 15.5, Math.toRadians(-45)), Math.toRadians(0))
      .splineToLinearHeading(new Pose2d(-11.25, 31, Math.toRadians(90)), Math.toRadians(0))
      .lineToY(51)
      .build());
    
    meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
      .setDarkMode(true)
      .setBackgroundAlpha(0.95f)
      .addEntity(myBot)
      .start();
  }
}