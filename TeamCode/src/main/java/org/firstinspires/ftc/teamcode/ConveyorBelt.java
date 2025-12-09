package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import androidx.annotation.NonNull;


public class ConveyorBelt {
    // private final CRServo servo;
    private final Telemetry telemetry;
    private double desiredSpeed = 1;
    public final CRServo servoForward;
    public final CRServo servoBackward;


    ConveyorBelt(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry, String color) {
        telemetry = theTelemetry;
        // servo = hardwareMap.get(CRServo.class, color + "_conveyor");
        // servo = hardwareMap.get(CRServo.class, direction + "_conveyor");
        servoForward = hardwareMap.get(CRServo.class, color + "_forward");
        servoBackward = hardwareMap.get(CRServo.class, color + "_backward");
        servoForward.setDirection(CRServo.Direction.FORWARD);
        servoBackward.setDirection(CRServo.Direction.REVERSE);

    }

    public void conveyForward() {
        servoForward.setPower(1);
        servoBackward.setPower(1);
        telemetry.addData("Conveyor", "Intake");
    }

    public void conveyBackward() {
        servoBackward.setPower(-1);
        servoForward.setPower(-1);
        telemetry.addData("Conveyor", "Eject");
    }

    public void stopConveying() {
        servoForward.setPower(0);
        servoBackward.setPower(0);
        telemetry.addData("Conveyor", "Stopped");

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