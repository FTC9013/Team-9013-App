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


    ConveyorBelt(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
    {
        telemetry = theTelemetry;
        servo = hardwareMap.get(CRServo.class, "servo");

        servo.setDirection(CRServo.Direction.FORWARD);

    }
    public void startConveying()
    {
        servo.setPower(1);
        telemetry.addData("Conveying in artifacts", "True");
    }
    public void stopConveying()
    {
        servo.setPower(0);
        telemetry.addData("Stop conveying in artifacts", "True");

    }
}

