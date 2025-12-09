package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import androidx.annotation.NonNull;


public class Intake {
    private final DcMotor intakeMotor;
    private final Telemetry telemetry;
    private double desiredSpeed = 1;


    Intake(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry) {
        telemetry = theTelemetry;
        intakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");

        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void startIntaking() {
        intakeMotor.setPower(desiredSpeed);
        telemetry.addData("Front Intake", "Intake");
    }

    public void startIntakingBackward() {
        intakeMotor.setPower(-desiredSpeed);
        telemetry.addData("Front Intake", "Eject");
    }

    public void stopIntaking() {
        intakeMotor.setPower(0);
        telemetry.addData("Front Intake", "Stopped");
    }

    public void startSpeedIncreasing() {
        desiredSpeed += 0.0001;
        if (desiredSpeed > 0.78) {
            desiredSpeed = 0.78;
        }
        intakeMotor.setPower(desiredSpeed);
        telemetry.addData("Intake speed in artifacts. Speed is ", desiredSpeed);
    }

    public void startSpeedDecreasing() {
        desiredSpeed -= 0.0001;
        if (desiredSpeed < 0) {
            desiredSpeed = 0;
        }
        intakeMotor.setPower(desiredSpeed);
        telemetry.addData("Intake speed in artifacts. Speed is ", desiredSpeed);
    }
}