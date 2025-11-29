package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class GateFlap {
    private final CRServo gateServo;
    private final Telemetry telemetry;
    private double desiredSpeed = 1;

    GateFlap(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry) {
        telemetry = theTelemetry;
        gateServo = hardwareMap.get(CRServo.class, "gateFlap");

    }

    public void openGateFlap() {
        gateServo.setDirection(CRServo.Direction.FORWARD);
        gateServo.setPower(desiredSpeed);
        telemetry.addData("Open gate flap. Speed is ", desiredSpeed);
    }

    public void closeGateFlap() {
        gateServo.setDirection(CRServo.Direction.REVERSE);
        gateServo.setPower(desiredSpeed);
        telemetry.addData("Close gate flap. Speed is ", desiredSpeed);
    }
}
