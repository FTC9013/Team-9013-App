package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import androidx.annotation.NonNull;




import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Launcher
{
    private final DcMotor launchMotor;
    private final Telemetry telemetry;
    private double desiredSpeed = 1;


    Launcher(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
    {
        telemetry = theTelemetry;
        launchMotor = hardwareMap.get(DcMotor.class, "LaunchMotor");

        launchMotor.setDirection(DcMotor.Direction.REVERSE);
    }
     public void startLaunching()
    {
        launchMotor.setPower(1);
        telemetry.addData("Launching artifacts", "True");
    }
    public void stopLaunching()
    {
        launchMotor.setPower(0);
        telemetry.addData("No more launch", "True");

    }
    public void launchSpeedIncreasing()
    {
        desiredSpeed += 0.0001;
        if (desiredSpeed > 1)
        {
            desiredSpeed = 1;
        }
        launchMotor.setPower(desiredSpeed);
        telemetry.addData("Intake speed in artifacts. Speed is " , desiredSpeed);
    }

    public void launchSpeedDecreasing()
    {
        desiredSpeed -= 0.0001;
        if (desiredSpeed < 0)
        {
            desiredSpeed = 0;
        }
        launchMotor.setPower(desiredSpeed);
        telemetry.addData("Intake speed in artifacts. Speed is " , desiredSpeed);
    }
}
