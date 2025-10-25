package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import androidx.annotation.NonNull;




import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Intake
{
    private final DcMotor intakeMotor;
    private final Telemetry telemetry;
    private double desiredSpeed = 1;


    Intake(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
    {
        telemetry = theTelemetry;
        intakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");

        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
    }
    public void startIntaking()
    {
        intakeMotor.setPower(desiredSpeed);
        telemetry.addData("Intake artifacts", "True");
    }
    public void stopIntaking()
    {
        intakeMotor.setPower(0);
        telemetry.addData("No more Intaking", "True");


    }
    public void startSpeedIncreasing()
    {
        desiredSpeed += 0.0001;
        if (desiredSpeed > 1)
        {
            desiredSpeed = 1;
        }
        intakeMotor.setPower(desiredSpeed);
        telemetry.addData("Intake speed in artifacts. Speed is " , desiredSpeed);
    }

    public void startSpeedDecreasing()
    {
        desiredSpeed -= 0.0001;
        if (desiredSpeed < 0)
        {
            desiredSpeed = 0;
        }
        intakeMotor.setPower(desiredSpeed);
        telemetry.addData("Intake speed in artifacts. Speed is " , desiredSpeed);
    }
}

