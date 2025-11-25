package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import androidx.annotation.NonNull;

public class Gate
{
  private final Servo gateServo;
  private final Telemetry telemetry;
  
  Gate(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
  {
    telemetry = theTelemetry;
    gateServo = hardwareMap.get(Servo.class, "GateServo");
    
  }
  
  public void open()
  {
    //gateServo.setDirection(Servo.Direction.FORWARD);
    gateServo.setPosition(1);
    
  }
  
  public void close()
  {
    gateServo.setPosition(0);
  }
  
  
}

