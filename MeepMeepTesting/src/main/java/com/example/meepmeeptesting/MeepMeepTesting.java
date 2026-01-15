package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
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
    
    Pose2d LAUNCH_POSITION = new Pose2d(-10, 14.5, Math.toRadians(-43));
    Pose2d SPIKE_PPG = new Pose2d(-15.75, 31, Math.toRadians(90));
    Pose2d SPIKE_PGP = new Pose2d(12, 31, Math.toRadians(90));
    Pose2d SPIKE_GPP = new Pose2d(35, 31, Math.toRadians(90));
    Pose2d SCANNING_POINT = new Pose2d(-16, 11.5, Math.toRadians(0));
    Double INTAKE = 45.0;
    Double BACK_UP = 31.0;
    Pose2d STARTING1 = new Pose2d(61.25, 11.5, Math.toRadians(0));
    Pose2d STARTING2 = new Pose2d(-61.25, 33, Math.toRadians(0));
    Vector2d OUT_OF_LAUNCH = new Vector2d(0, 20);
    RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
      // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
      .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
      .setDimensions(16.5, 17)
      .build();
    Action moveToScanningFirst = myBot.getDrive().actionBuilder(STARTING1)
      .setReversed(true)
      .lineToX(SCANNING_POINT.position.x)
      .build();
    
    Action moveToScanningSecond = myBot.getDrive().actionBuilder(STARTING2)
      .splineToSplineHeading(SCANNING_POINT, 0)
      .build();
    
    Action goToLaunch = myBot.getDrive().actionBuilder(SCANNING_POINT)
      //preloaded
      .splineToLinearHeading(LAUNCH_POSITION, 0)
      .build();
    
    Action gotoSpikePPG = myBot.getDrive().actionBuilder(LAUNCH_POSITION)
      //spike GPP
      .splineToLinearHeading(SPIKE_PPG, SPIKE_PPG.heading)
      //.stopAndAdd(shooter.startIntakingAction())
      .lineToY(INTAKE)
      //.stopAndAdd(shooter.stopAllMotorsAction())
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      //.stopAndAdd(shooter.shootGPP())
      .build();
    Action gotoSpikeGPP = myBot.getDrive().actionBuilder(LAUNCH_POSITION)
      //spike GPP
      .splineToLinearHeading(SPIKE_GPP, SPIKE_GPP.heading)
      //.stopAndAdd(shooter.startIntakingAction())
      .lineToY(INTAKE)
      //.stopAndAdd(shooter.stopAllMotorsAction())
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      //.stopAndAdd(shooter.shootGPP())
      .build();
    
    Action gotoSpikePGP = myBot.getDrive().actionBuilder(LAUNCH_POSITION)
      //spike PGP
      .splineToLinearHeading(SPIKE_PGP, SPIKE_PGP.heading)
      //.stopAndAdd(shooter.startIntakingAction())
      .lineToY(INTAKE)
      //.stopAndAdd(shooter.stopAllMotorsAction())
      .lineToY(BACK_UP)
      .splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      //.stopAndAdd(shooter.shootPGP())
      .build();
    
    Action collectPPG = myBot.getDrive().actionBuilder(LAUNCH_POSITION)
      //spike PPG
      .splineToLinearHeading(SPIKE_PPG, SPIKE_PPG.heading)
      //.turnTo(0)
      //.stopAndAdd(shooter.startIntakingAction())
      .strafeToConstantHeading(new Vector2d(SPIKE_PPG.position.x, 38))
      .strafeTo(new Vector2d(-7.75, 38))
      .strafeToConstantHeading(new Vector2d(-7.75, INTAKE))
      //.stopAndAdd(shooter.stopAllMotorsAction())
      //.lineToY(BACK_UP)
      //.splineToLinearHeading(LAUNCH_POSITION, LAUNCH_POSITION.heading)
      .build();
    
    Action getOut = myBot.getDrive().actionBuilder(LAUNCH_POSITION)
      // out of launch_position
      .strafeTo(OUT_OF_LAUNCH).build();
    
    Action strafe = myBot.getDrive().actionBuilder(STARTING1)
      .splineToLinearHeading(LAUNCH_POSITION, Math.toRadians(0))
      .build();
    myBot.runAction(collectPPG);
    
    meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
      .setDarkMode(true)
      .setBackgroundAlpha(0.95f)
      .addEntity(myBot)
      .start();
  }
}
//FART heheh heheh heheh heheh