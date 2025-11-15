package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import androidx.annotation.NonNull;

public class Gate
{
  private final Servo gateServo;
  private final Telemetry telemetry;
  
  Gate(@NonNull HardwareMap hardwareMap, Servo gateServo, Telemetry theTelemetry)
  {
    this.gateServo = gateServo;
    telemetry = theTelemetry;
    gateServo = hardwareMap.get(Servo.class, "LaunchMotor");
    
  }
  
  public void open()
  {
  
  }
  
  public void close()
  {
  
  }
  
}

