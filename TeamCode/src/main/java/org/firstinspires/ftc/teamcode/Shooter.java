package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

import androidx.annotation.NonNull;


public class Shooter
{
  public final Launcher launchWheel;
  private final Telemetry telemetry;
  public final ConveyorBelt conveyorBeltG;
  public final ConveyorBelt conveyorBeltP;
  public final Intake intake;
  public List<String> MotifBalls;
  public final double chargingTime = 4;
  
  Shooter(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
  {
    telemetry = theTelemetry;
    launchWheel = new Launcher(hardwareMap, telemetry);
    conveyorBeltG = new ConveyorBelt(hardwareMap, telemetry, "green");
    conveyorBeltP = new ConveyorBelt(hardwareMap, telemetry, "purple");
    intake = new Intake(hardwareMap, telemetry);
  }
  
  /*
      public void launchArtifact() {
          conveyorForward.startConveyingForward();
          telemetry.addLine("Conveying artifact");
          launchWheel.startLaunching();
          telemetry.addLine("Launching artifact");
          launchWheel.stopLaunching();
          conveyorForward.stopConveying();
          telemetry.update();

      }
  */
  public void startLaunchMotor()
  {
    launchWheel.startLaunching();
    telemetry.addLine("Launching artifact");
    telemetry.update();
  }
  
  public void stopLaunchMotor()
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
  
  public void startIntakingBackwards()
  {
    intake.startIntakingBackward();
    telemetry.addLine("reversing intake");
    telemetry.update();
  }
  
  public class StartIntakingAction implements Action
  {
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      startIntaking();
      return false;
    }
  }
  
  public class StopAllMotorsAction implements Action
  {
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      stopIntaking();
      stopLaunchMotor();
      return false;
    }
  }
  
  public class ConveyorAction implements Action
  {
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      conveyorBeltG.conveyForward();
      conveyorBeltP.conveyForward();
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
  
  public class ShootGPP implements Action
  {
    private boolean initialized = false;
    ElapsedTime runtime = new ElapsedTime();
    
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      if (!initialized)
      {
        runtime.reset();
        startLaunchMotor();
        initialized = true;
      }
      if (launchWheel.reachedDesiredSpeed())
      {
        conveyorBeltG.conveyForward();
        
      }
      if (launchWheel.hasSpeedDecreasedQuestionMark())
      {
        conveyorBeltG.stopConveying();
        conveyorBeltP.conveyForward();
      }
      if (!launchWheel.hasSpeedDecreasedQuestionMark())
      {
        conveyorBeltP.stopConveying();
        stopLaunchMotor();
        return false;
      }
      return true;
    }
  }
  
  public class ShootPGP implements Action
  {
    private boolean initialized = false;
    ElapsedTime runtime = new ElapsedTime();
    
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      if (!initialized)
      {
        runtime.reset();
        startLaunchMotor();
        initialized = true;
      }
      if (runtime.seconds() > 3)
      {
        conveyorBeltP.conveyForward();
      }
      if (runtime.seconds() > 4)
      {
        conveyorBeltP.stopConveying();
      }
      if (runtime.seconds() > 5)
      {
        conveyorBeltG.conveyForward();
      }
      if (runtime.seconds() > 7.5)
      {
        conveyorBeltP.conveyForward();
        conveyorBeltG.stopConveying();
      }
      if (runtime.seconds() > 9)
      {
        stopLaunchMotor();
        conveyorBeltP.stopConveying();
        return false;
      }
      return true;
    }
  }
  
  public class ShootPPG implements Action
  {
    private boolean initialized = false;
    ElapsedTime runtime = new ElapsedTime();
    
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      if (!initialized)
      {
        runtime.reset();
        startLaunchMotor();
        initialized = true;
      }
      if (runtime.seconds() > 2.5)
      {
        conveyorBeltP.conveyForward();
      }
      if (runtime.seconds() > 6.75)
      {
        conveyorBeltG.conveyForward();
        conveyorBeltP.stopConveying();
      }
      if (runtime.seconds() > 8.75)
      {
        stopLaunchMotor();
        conveyorBeltG.stopConveying();
        return false;
      }
      return true;
    }
  }
  
  public Action intakingAction()
  {
    return new IntakingAction();
  }
  
  
  public Action shootGPP()
  {
    return new ShootGPP();
  }
  
  public Action shootPGP()
  {
    return new ShootPGP();
  }
  
  public Action shootPPG()
  {
    return new ShootPPG();
  }
  
  public Action conveyorAction()
  {
    return new ConveyorAction();
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
