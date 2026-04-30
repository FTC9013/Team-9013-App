package org.firstinspires.ftc.teamcode;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

@Autonomous(name = "Pedro Pathing Autonomous", group = "Autonomous")
@Configurable // Panels
public class PedroAutonomous extends OpMode
{
  private TelemetryManager panelsTelemetry; // Panels Telemetry instance
  public Follower follower; // Pedro Pathing follower instance
  private int pathState; // Current autonomous path state (state machine)
  private Paths paths; // Paths defined in the Paths class
  
  @Override
  public void init()
  {
    Scheduler.reset();
    panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    
    follower = Constants.createFollower(hardwareMap);
    follower.setStartingPose(new Pose(72, 8, Math.toRadians(90)));
    
    paths = new Paths(follower); // Build paths
    
    panelsTelemetry.debug("Status", "Initialized");
    panelsTelemetry.update(telemetry);
  }
  
  @Override
  public void start()
  {
    schedule(autoRoutine());
  }
  
  @Override
  public void loop()
  {
    follower.update();
    Scheduler.execute();
    //org.firstinspires.ftc.teamcode.pedroPathing.Drawing.drawDebug(follower);
    // Feedback to Driver Hub for debugging
    telemetry.addData("x", follower.getPose().getX());
    telemetry.addData("y", follower.getPose().getY());
    telemetry.addData("heading", follower.getPose().getHeading());
    telemetry.update();
  }
  
  
  public static class Paths
  {
    public PathChain line1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;
    public PathChain Path7;
    public PathChain Path8;
    public PathChain Path9;
    
    public Paths(Follower follower)
    {
      line1 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(110.709, 134.992),
            
            new Pose(83.906, 100.630)
          )
        ).setConstantHeadingInterpolation(Math.toRadians(90))
        
        .build();
      
      Path2 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(83.906, 100.630),
            
            new Pose(96.457, 96.795)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(45))
        
        .build();
      
      Path3 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(96.457, 96.795),
            
            new Pose(104.063, 84.134)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
        
        .build();
      
      Path4 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(104.063, 84.134),
            
            new Pose(125.339, 83.677)
          )
        ).setTangentHeadingInterpolation()
        
        .build();
      
      Path5 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(125.339, 83.677),
            
            new Pose(95.827, 96.472)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
        
        .build();
      
      Path6 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(95.827, 96.472),
            
            new Pose(101.071, 59.268)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
        
        .build();
      
      Path7 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(101.071, 59.268),
            
            new Pose(130.260, 59.488)
          )
        ).setTangentHeadingInterpolation()
        
        .build();
      
      Path8 = follower.pathBuilder().addPath(
          new BezierCurve(
            new Pose(130.260, 59.488),
            new Pose(108.380, 65.419),
            new Pose(96.106, 96.626)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
        
        .build();
      
      Path9 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(96.106, 96.626),
            
            new Pose(72.087, 48.268)
          )
        ).setTangentHeadingInterpolation()
        .setReversed()
        .build();
    }
  }
  
  
  public Command autoRoutine()
  {
    return sequential(
      follow(follower, paths.line1),
      /* Score Preload Command*/
      follow(follower, paths.Path2, true),
      /* Grab Sample Command*/
      follow(follower, paths.Path3, true),
      /* Score Sample Command*/
      follow(follower, paths.Path4, true),
      /* Grab Sample Command*/
      follow(follower, paths.Path5, true),
      /* Score Sample Command*/
      follow(follower, paths.Path6, true),
      /* Grab Sample Command*/
      follow(follower, paths.Path7, true),
      follow(follower, paths.Path8, true),
      follow(follower, paths.Path9, true)
      /* Score Sample Command*/
    );
  }
}

