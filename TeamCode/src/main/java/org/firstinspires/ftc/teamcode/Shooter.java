package org.firstinspires.ftc.teamcode;//

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;


public class Shooter
{
  public final Launcher launchWheel;
  private final Telemetry telemetry;
  public final ConveyorBelt conveyorBeltG;
  public final ConveyorBelt conveyorBeltP;
  public final Intake intake;
  
  Shooter(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
  {
    telemetry = theTelemetry;
    launchWheel = new Launcher(hardwareMap, telemetry);
    conveyorBeltG = new ConveyorBelt(hardwareMap, telemetry, "green");
    conveyorBeltP = new ConveyorBelt(hardwareMap, telemetry, "purple");
    intake = new Intake(hardwareMap, telemetry);
  }
  
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
  
  public class ConveyorActionBackwards implements Action
  {
    ElapsedTime runtime = new ElapsedTime();
    
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      conveyorBeltG.conveyBackward();
      conveyorBeltP.conveyBackward();
      if (runtime.seconds() > 0.75)
      {
        conveyorBeltG.stopConveying();
        conveyorBeltP.stopConveying();
        return false;
      } else
      {
        return true;
      }
    }
  }
  
  
  public enum States
  {
    Init,
    Charging,
    Launching,
    Stopping
    
  }
  
  public class Shoot implements Action
  {
    ElapsedTime runtime = new ElapsedTime();
    States currentState = States.Init;
    ArrayList<String> colours;
    
    Shoot(ArrayList<String> coloursPara)
    {
      colours = coloursPara;
    }
    
    @Override
    public boolean run(@NonNull TelemetryPacket packet)
    {
      telemetry.addData("Current state is:", currentState);
      telemetry.addData("Time in state", runtime.seconds());
      if (!colours.isEmpty())
      {
        telemetry.addData("Next ball is:", colours.get(0));
      }
      launchWheel.printOutputSpeed();
      telemetry.update();
      
      if (currentState == States.Init)
      {
        runtime.reset();
        startLaunchMotor();
        currentState = States.Charging;
        
        
      } else if (currentState == States.Charging)
      {
        if (launchWheel.reachedDesiredSpeed() || runtime.seconds() > 5)
        {
          
          if (colours.get(0).equals("Green"))
          {
            conveyorBeltG.conveyForward();
          } else
          {
            conveyorBeltP.conveyForward();
          }
          runtime.reset();
          currentState = States.Launching;
        }
        
        
      } else if (currentState == States.Launching)
      {
        if (launchWheel.hasSpeedDecreasedQuestionMark() || runtime.seconds() > 3)
        {
          colours.remove(0);
          conveyorBeltG.stopConveying();
          conveyorBeltP.stopConveying();
          if (colours.isEmpty())
          {
            runtime.reset();
            currentState = States.Stopping;
          } else
          {
            runtime.reset();
            currentState = States.Charging;
          }
        }
        
        
      } else if (currentState == States.Stopping)
      {
        stopLaunchMotor();
        
        return false;
      }
      return true;
    }
  }
  
  //public Action intakingAction()
  //{
  //  return new IntakingAction();
  //}
  
  
  public Action shootMotif(Motif pattern)
  {
    if (pattern == Motif.GPP)
    {
      return new Shoot(new ArrayList<>(Arrays.asList("Green", "Purple", "Purple")));
    } else if (pattern == Motif.PGP)
    {
      return new Shoot(new ArrayList<>(Arrays.asList("Purple", "Green", "Purple")));
    } else
    {
      return new Shoot(new ArrayList<>(Arrays.asList("Purple", "Purple", "Green")));
    }
  }
  
  public Action conveyorAction()
  {
    return new ConveyorAction();
  }
  
  public Action conveyorActionBackwards()
  {
    return new ConveyorActionBackwards();
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
