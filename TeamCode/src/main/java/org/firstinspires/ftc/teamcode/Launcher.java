package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LED;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import androidx.annotation.NonNull;


public class Launcher
{
  private final DcMotorEx launchMotor;
  private final LED rpmLEDPurple;
  private final LED rpmLEDGreen;
  private final Telemetry telemetry;
  private double desiredSpeed = 1640;
  private boolean isSpinning = false;
  
  
  Launcher(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
  {
    telemetry = theTelemetry;
    launchMotor = hardwareMap.get(DcMotorEx.class, "Launcher");
    rpmLEDPurple = hardwareMap.get(LED.class, "LED_Purple");
    rpmLEDGreen = hardwareMap.get(LED.class, "LED_Green");
    rpmLEDPurple.off();
    rpmLEDGreen.off();
    launchMotor.setDirection(DcMotor.Direction.REVERSE);
  }
  
  public void setSpeed(double speed)
  {
    desiredSpeed = speed;
  }
  
  public void startLaunching()
  {
    launchMotor.setVelocity(desiredSpeed);
    isSpinning = true;
    telemetry.addData("Launching artifacts", "True");
  }
  
  public boolean reachedDesiredSpeed()
  {
    if (desiredSpeed - 15 <= launchMotor.getVelocity() && launchMotor.getVelocity() <= desiredSpeed + 15)
    {
      return true;
    } else
    {
      return false;
    }
  }
  
  public boolean hasSpeedDecreasedQuestionMark()
  {
    if (desiredSpeed - launchMotor.getVelocity() >= 40)
    {
      return true;
    } else
    {
      return false;
    }
  }
  
  public void startLaunchingBackward()
  {
    launchMotor.setVelocity(-desiredSpeed / 2);
    telemetry.addData("Launching artifacts", "True");
  }
  
  public void stopLaunching()
  {
    launchMotor.setPower(0);
    isSpinning = false;
    telemetry.addData("No more launch", "True");
    
  }
  
  public void launchSpeedIncreasing()
  {
    desiredSpeed += 160;
    isSpinning = true;
    
    launchMotor.setVelocity(desiredSpeed);
    telemetry.addData("Intake speed in artifacts. Speed is ", desiredSpeed);
    telemetry.update();
  }
  
  public void launchSpeedDecreasing()
  {
    desiredSpeed -= 160;
    isSpinning = true;
    if (desiredSpeed < 0)
    {
      desiredSpeed = 0;
      isSpinning = false;
    }
    launchMotor.setVelocity(desiredSpeed);
    telemetry.addData("Intake speed in artifacts. Speed is ", desiredSpeed);
    telemetry.update();
  }
  
  public void printOutputSpeed()
  {
    telemetry.addData("Launch speed: ", desiredSpeed);
    telemetry.addData("Current speed yo: ", launchMotor.getVelocity());
    if (desiredSpeed - 15 <= launchMotor.getVelocity() && launchMotor.getVelocity() <= desiredSpeed + 15)
    {
      rpmLEDPurple.on();
      rpmLEDGreen.on();
    } else
    {
      rpmLEDPurple.off();
      rpmLEDGreen.off();
    }
  }
  
  public void toggle()
  {
    if (isSpinning)
    {
      stopLaunching();
    } else
    {
      startLaunching();
    }
  }
}
