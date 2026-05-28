package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import androidx.annotation.NonNull;


public class ServoTest
{
  // private final CRServo servo;
  private final Telemetry telemetry;
  private double desiredSpeed = 1;
  public final Servo servoForward;
  
  
  ServoTest(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
  {
    telemetry = theTelemetry;
    //  servo = hardwareMap.get(CRServo.class, color + "_conveyor");
    // servo = hardwareMap.get(CRServo.class, direction + "_conveyor");
    servoForward = hardwareMap.get(Servo.class, "servo");
    
  }
  
  public void conveyForward()
  {
    servoForward.setPosition(1);
    telemetry.addLine("Adding power yo");
  }
  
  public void conveyBackwardTwo()
  {
    servoForward.setPosition(-1);
    telemetry.addLine("Adding power yo");
    
  }
  
  public void stopConveying()
  {
    servoForward.setPosition(0);
    telemetry.addData("Stop conveying in artifact. Speed is ", desiredSpeed);
    
  }
  /*
  public void startConveyingForward()
  {
    
    servo.setDirection(CRServo.Direction.FORWARD);
    servo.setPower(desiredSpeed);
    telemetry.addData("Conveying in artifacts. Speed is ", desiredSpeed);
  }
  
  public void startConveyingBackward()
  {
    servo.setDirection(CRServo.Direction.REVERSE);
    servo.setPower(desiredSpeed);
    
    telemetry.addData("Conveying in artifacts. Speed is ", desiredSpeed);
  }
  
  public void startConveyingIncreasing()
  {
    servo.setDirection(CRServo.Direction.FORWARD);
    desiredSpeed += 0.0001;
    if (desiredSpeed > 1)
    {
      desiredSpeed = 1;
    }
    servo.setPower(desiredSpeed);
    telemetry.addData("Conveying in artifacts. Speed is ", desiredSpeed);
  }
  
  public void startConveyingDecreasing()
  {
    servo.setDirection(CRServo.Direction.FORWARD);
    desiredSpeed -= 0.0001;
    if (desiredSpeed < 0)
    {
      desiredSpeed = 0;
    }
    servo.setPower(desiredSpeed);
    telemetry.addData("Conveying in artifacts. Speed is ", desiredSpeed);
  }
  */
  
}