package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter {
    private final Launcher launchWheel;
    private final Telemetry telemetry;
    private final ConveyorBelt conveyor;
    private final Intake intake;

    Shooter(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry) {
        telemetry = theTelemetry;
        launchWheel = new Launcher(hardwareMap, telemetry);
        conveyor = new ConveyorBelt(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
    }
    public void launchArtifact()
    {
        conveyor.startConveying();
        telemetry.addLine("Conveying artifact");
        launchWheel.startLaunching();
        telemetry.addLine("Launching artifact");
        launchWheel.stopLaunching();
        conveyor.stopConveying();
        telemetry.update();

    }
    public void startLaunching()
    {
        launchWheel.startLaunching();
        telemetry.addLine("Launching artifact");
        telemetry.update();
    }
    public void stopLaunching()
    {
        launchWheel.stopLaunching();
        telemetry.addLine("Stop launching artifact");
        telemetry.update();
    }
    public void startIntaking()
    {
        intake.startIntaking();
        telemetry.addLine("Intaking artifact");
        telemetry.update();
    }
    public void stopIntaking()
    {
        intake.stopIntaking();
        telemetry.addLine("Stop intaking artifact");
        telemetry.update();
    }
}
