package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import androidx.annotation.NonNull;




import org.firstinspires.ftc.robotcore.external.Telemetry;


public class ConveyorBelt
{
    private final CRServo servo;
    private final Telemetry telemetry;
    private double desiredSpeed;


    ConveyorBelt(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
    {
        telemetry = theTelemetry;
        servo = hardwareMap.get(CRServo.class, "servo");

        servo.setDirection(CRServo.Direction.FORWARD);
        desiredSpeed = 1;

    }
    public void startConveying()
    {
        desiredSpeed = 1;
        servo.setPower(desiredSpeed);
        telemetry.addData("Conveying in artifacts. Speed is " , desiredSpeed);
    }
    public void startConveyingIncreasing()
    {
        desiredSpeed += 0.0001;
        if (desiredSpeed > 1)
        {
            desiredSpeed = 1;
        }
        servo.setPower(desiredSpeed);
        telemetry.addData("Conveying in artifacts. Speed is " , desiredSpeed);
    }

    public void startConveyingDecreasing()
    {
        desiredSpeed -= 0.0001;
        if (desiredSpeed < 0)
        {
            desiredSpeed = 0;
        }
        servo.setPower(desiredSpeed);
        telemetry.addData("Conveying in artifacts. Speed is " , desiredSpeed);
    }
    public void stopConveying()
    {
        servo.setPower(0);
        telemetry.addData("Stop conveying in artifact. Speed is " , desiredSpeed);

    }
}

