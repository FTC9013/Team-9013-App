package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import androidx.annotation.NonNull;


public class CurvingShooter
{
  private final Launcher launchWheel;
  private final Telemetry telemetry;
  private final ConveyorBelt conveyorForward;
  private final ConveyorBelt conveyorBackward;
  private final Intake intake;
  
  
  CurvingShooter(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
  {
    telemetry = theTelemetry;
    launchWheel = new Launcher(hardwareMap, telemetry, "purple");
    conveyorForward = new ConveyorBelt(hardwareMap, telemetry, "forward");
    conveyorBackward = new ConveyorBelt(hardwareMap, telemetry, "backward");
    intake = new Intake(hardwareMap, telemetry);
  }
  
  /*
      public void launchGreenArtifact() {
          conveyorForward.startConveyingForward();
          telemetry.addLine("Conveying artifact");
          launchWheel.startLaunching();
          telemetry.addLine("Launching artifact");
          launchWheel.stopLaunching();
          conveyorForward.stopConveying();
          telemetry.update();
      }

      public void launchPurpleArtifact() {
          conveyorForward.startConveyingForward();
          telemetry.addLine("Conveying artifact");
          launchWheel.startLaunching();
          telemetry.addLine("Launching artifact");
          launchWheel.stopLaunching();
          conveyorForward.stopConveying();
          telemetry.update();
      }
  */
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
    return new CurvingShooter.IntakingAction();
  }
  
  public Action shootingAction()
  {
    return new CurvingShooter.ShootingAction();
  }
  
  public Action startIntakingAction()
  {
    return new CurvingShooter.StartIntakingAction();
  }
  
  public Action stopAllMotorsAction()
  {
    return new CurvingShooter.StopAllMotorsAction();
  }
}


