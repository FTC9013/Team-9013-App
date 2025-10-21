package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting
{
  public static void main(String[] args)
  {
    MeepMeep meepMeep = new MeepMeep(800);
    
    Pose2d LAUNCH_POSITION = new Pose2d(-40.25, 15.5, Math.toRadians(-45));
    Pose2d SPIKE1 = new Pose2d(-11.25, 31, Math.toRadians(90));
    Pose2d SPIKE2 = new Pose2d(12, 31, Math.toRadians(90));
    Pose2d SPIKE3 = new Pose2d(35, 31, Math.toRadians(90));
    Double INTAKE = 45.0;
    Double BACK_UP = 31.0;
    Pose2d STARTING1 = new Pose2d(-61.25, 11.5, 0);
    RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
      // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
      .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
      .setDimensions(16.5, 17)
      .build();
    
    myBot.runAction(myBot.getDrive().actionBuilder(STARTING1)
      .lineToX(-31.25)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .splineToLinearHeading(SPIKE1, SPIKE1.heading)
      .lineToY(INTAKE)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .splineToLinearHeading(SPIKE2, SPIKE2.heading)
      .lineToY(INTAKE)
      .lineToY(BACK_UP)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      //.lineToSplineHeading(SPIKE3, SPIKE3.heading)
      .lineToY(INTAKE)
      .lineToY(BACK_UP)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .strafeTo(new Vector2d(-16, 38))
      .build());
    
    meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
      .setDarkMode(true)
      .setBackgroundAlpha(0.95f)
      .addEntity(myBot)
      .start();
  }
}
//FART heheh