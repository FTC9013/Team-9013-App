package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import androidx.annotation.NonNull;

@Disabled

public class Shooter
{
  private final Launcher launchWheel;
  private final Telemetry telemetry;
  private final ConveyorBelt conveyorForward;
  private final Intake intake;
  
  private final Servo gate;
  
  
  Shooter(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
  {
    telemetry = theTelemetry;
    launchWheel = new Launcher(hardwareMap, telemetry);
    conveyorForward = new ConveyorBelt(hardwareMap, telemetry, "forward");
    intake = new Intake(hardwareMap, telemetry);
  }
  
  public void launchArtifact()
  {
    conveyorForward.startConveyingForward();
    telemetry.addLine("Conveying artifact");
    launchWheel.startLaunching();
    telemetry.addLine("Launching artifact");
    launchWheel.stopLaunching();
    conveyorForward.stopConveying();
    telemetry.update();
    
  }
  
  public void startLaunching()
  {
    launchWheel.startLaunching();
    telemetry.addLine("Launching artifact");
    telemetry.update();
  }
  
  public void stopLaunching()
  {
    launchWheel.stopLaunching();
    telemetry.addLine("Stop launching artifact");
    telemetry.update();
  }
  
  public void startIntaking()
  {
    intake.startIntaking();
    telemetry.addLine("Intaking artifact");
    telemetry.update();
  }
  
  public void stopIntaking()
  {
    intake.stopIntaking();
    telemetry.addLine("Stop intaking artifact");
    telemetry.update();
  }
  
  public class StartIntakingAction implements Action
  {
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      startIntaking();
      return true;
    }
  }
  
  public class StopAllMotorsAction implements Action
  {
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      stopIntaking();
      stopLaunching();
      return false;
    }
  }
  
  public class IntakingAction implements Action
  {
    private boolean initialized = false;
    ElapsedTime runtime = new ElapsedTime();
    
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      if (!initialized)
      {
        runtime.reset();
        startIntaking();
        initialized = true;
      }
      if (runtime.seconds() > 7)
      {
        stopIntaking();
        return false;
      }
      return true;
    }
  }
  
  public class ShootingAction implements Action
  {
    private boolean initialized = false;
    ElapsedTime runtime = new ElapsedTime();
    
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      if (!initialized)
      {
        runtime.reset();
        startLaunching();
        initialized = true;
      }
      if (runtime.seconds() > 7)
      {
        stopLaunching();
        return false;
      }
      return true;
    }
    
    
  }
  
  public Action intakingAction()
  {
    return new IntakingAction();
  }
  
  public Action shootingAction()
  {
    return new ShootingAction();
  }
  
  public Action startIntakingAction()
  {
    return new StartIntakingAction();
  }
  
  public Action stopAllMotorsAction()
  {
    return new StopAllMotorsAction();
  }
}
