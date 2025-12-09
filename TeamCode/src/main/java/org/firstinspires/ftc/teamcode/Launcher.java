package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import androidx.annotation.NonNull;


public class Launcher {
    private final DcMotor launchMotor;
    private final Telemetry telemetry;
    private double desiredSpeed = 0.78;


    Launcher(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry, String color) {
        telemetry = theTelemetry;
        launchMotor = hardwareMap.get(DcMotor.class, color + "LaunchMotor");

        launchMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void startLaunching() {
        launchMotor.setPower(desiredSpeed);
        telemetry.addData("Launcher spinning. Speed", "%.0f%%", desiredSpeed * 100);
    }

    public void startLaunchingBackward() {
        launchMotor.setPower(-0.5);
        telemetry.addData("Launcher reversing. Speed", "%.0f%%", desiredSpeed * 100);
    }

    public void stopLaunching() {
        launchMotor.setPower(0);
        telemetry.addData("Launcher stopped. Speed", "%.0f%%", desiredSpeed * 100);

    }

    public void launchSpeedIncreasing() {
        desiredSpeed += 0.01;
        if (desiredSpeed > 1) {
            desiredSpeed = 1;
        }
    }

    public void launchSpeedDecreasing() {
        desiredSpeed -= 0.01;
        if (desiredSpeed < 0.05) {
            desiredSpeed = 0.05;
        }
    }
}
//suhanya loves  ragebating