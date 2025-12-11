package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import androidx.annotation.NonNull;


public class Launcher {
    private final DcMotor launchMotor;
    private final Telemetry telemetry;
    private double desiredSpeed = 0.78;
    private boolean isSpinning = false;


    Launcher(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry, String color) {
        telemetry = theTelemetry;
        launchMotor = hardwareMap.get(DcMotor.class, color + "_Launcher");
        if (color == "purple") {
            launchMotor.setDirection(DcMotor.Direction.REVERSE);
        }
    }

    public void startLaunching() {
        launchMotor.setPower(desiredSpeed);
        isSpinning = true;
        telemetry.addData("Launching artifacts", "True");
    }

    public void startLaunchingBackward() {
        launchMotor.setPower(-0.5);
        telemetry.addData("Launching artifacts", "True");
    }

    public void stopLaunching() {
        launchMotor.setPower(0);
        isSpinning = false;
        telemetry.addData("No more launch", "True");

    }

    public void launchSpeedIncreasing() {
        desiredSpeed += 0.0001;
        isSpinning = true;
        if (desiredSpeed > 1) {
            desiredSpeed = 1;
        }
        launchMotor.setPower(desiredSpeed);
        telemetry.addData("Intake speed in artifacts. Speed is ", desiredSpeed);
    }

    public void launchSpeedDecreasing() {
        desiredSpeed -= 0.0001;
        isSpinning = true;
        if (desiredSpeed < 0) {
            desiredSpeed = 0;
            isSpinning = false;
        }
        launchMotor.setPower(desiredSpeed);
        telemetry.addData("Intake speed in artifacts. Speed is ", desiredSpeed);
    }

    public void toggle() {
        if (isSpinning) {
            stopLaunching();
        } else {
            startLaunching();
        }
    }
}
