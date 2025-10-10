package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import androidx.annotation.NonNull;




import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Intake
{
    private final DcMotor intakeMotor;
    private final Telemetry telemetry;


    Intake(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
    {
        telemetry = theTelemetry;
        intakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");

        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
    }
    public void startIntaking()
    {
        intakeMotor.setPower(1);
        telemetry.addData("Intake artifacts", "True");
    }
    public void stopIntaking()
    {
        intakeMotor.setPower(0);
        telemetry.addData("No more Intaking", "True");


    }
}

