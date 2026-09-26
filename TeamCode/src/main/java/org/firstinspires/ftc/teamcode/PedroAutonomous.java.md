package org.firstinspires.ftc.teamcode;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
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
  public ServoTest servoMotor;
  public Follower follower; // Pedro Pathing follower instance
  private int pathState; // Current autonomous path state (state machine)
  private Paths paths; // Paths defined in the Paths class
  
  @Override
  public void init()
  {
    Scheduler.reset();
    panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    servoMotor = new ServoTest(hardwareMap, telemetry);
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
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    
    public Paths(Follower follower)
    {
      Path1 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(72.553, 8.184),
            
            new Pose(72.332, 71.889)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
        
        .build();
      
      Path2 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(72.332, 71.889),
            
            new Pose(23.456, 84.009)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
        
        .build();
      
      Path3 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(23.456, 84.009),
            
            new Pose(120.622, 71.724)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
        
        .build();
    }
  }
  
  int counter = 0;
  
  
  public Command autoRoutine()
  {
    Command runServo = Command.build().setExecute(() -> {
        servoMotor.conveyForward();
        counter += 1;
      })
      .setDone(() -> counter > 1);
    return sequential(
      follow(follower, paths.Path1),
      runServo,
      /* Score Preload Command*/
      follow(follower, paths.Path2, true),
      /* Grab Sample Command*/
      follow(follower, paths.Path3, true)
    
    );
  }
}

