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


    Launcher(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
    {
        telemetry = theTelemetry;
        launchMotor = hardwareMap.get(DcMotor.class, "LaunchMotor");

        launchMotor.setDirection(DcMotor.Direction.FORWARD);
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
}
