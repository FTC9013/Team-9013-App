package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "Test", group = "Linear opMode")
public class Test extends LinearOpMode {

    // public ConveyorBelt conveyorForward = null;
    public ConveyorBelt conveyorBelt_purple = null;
    public ConveyorBelt conveyorBelt_green = null;
    public Launcher launcher_purple = null;
    public Launcher launcher_green = null;
    public Intake intake = null;
    boolean launcherToggle_purple = false;
    boolean launcherToggle_green = false;

    //public ConveyorBelt conveyorBackward;

    //public MecanumDriveChassis ServoTest;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        conveyorBelt_purple = new ConveyorBelt(hardwareMap, telemetry, "purple");
        conveyorBelt_green = new ConveyorBelt(hardwareMap, telemetry, "green");
        // conveyorForward = new ConveyorBelt(hardwareMap, telemetry, "forward");
        // conveyorBackward = new ConveyorBelt(hardwareMap, telemetry, "backward");
        launcher_purple = new Launcher(hardwareMap, telemetry, "purple");
        launcher_green = new Launcher(hardwareMap, telemetry, "green");
        intake = new Intake(hardwareMap, telemetry);
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        waitForStart();
        while (opModeIsActive()) {
        /*    if (gamepad1.right_bumper) {
                conveyorForward.startConveyingForward();
                telemetry.addLine("Pressing right bumper");
            }
            if (gamepad1.left_bumper) {
                conveyorBackward.startConveyingBackward();
                telemetry.addLine("Pressing left bumper");
            }
            if (gamepad1.a) {
                conveyorForward.startConveyingForward();
                telemetry.addLine("Pressing key A");
            }
        */
            // Store the gamepad values from the previous loop iteration in
            // previousGamepad1/2 to be used in this loop iteration.
            // This is equivalent to doing this at the end of the previous
            // loop iteration, as it will run in the same order except for
            // the first/last iteration of the loop.
            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);

            // Store the gamepad values from this loop iteration in
            // currentGamepad1/2 to be used for the entirety of this loop iteration.
            // This prevents the gamepad values from changing between being
            // used and stored in previousGamepad1/2.
            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);

            telemetry.addLine("Purple Status");

            if (currentGamepad1.x && !previousGamepad1.x) {
                // This will set intakeToggle to true if it was previously false
                // and intakeToggle to false if it was previously true,
                // providing a toggling behavior.
                launcherToggle_purple = !launcherToggle_purple;
            }

            if (launcherToggle_purple) {
                launcher_purple.startLaunching();
            } else {
                launcher_purple.stopLaunching();
            }

            if (gamepad1.left_bumper) {
                conveyorBelt_purple.conveyForward();
            } else if (gamepad1.left_trigger > 0) {
                conveyorBelt_purple.conveyBackward();
            } else {
                conveyorBelt_purple.stopConveying();
            }

            telemetry.addLine();
            telemetry.addLine("Green Status");

            if (currentGamepad1.b && !previousGamepad1.b) {
                // This will set intakeToggle to true if it was previously false
                // and intakeToggle to false if it was previously true,
                // providing a toggling behavior.
                launcherToggle_green = !launcherToggle_green;
            }

            if (launcherToggle_green) {
                launcher_green.startLaunching();
            } else {
                launcher_green.stopLaunching();
            }

            if (gamepad1.right_bumper) {
                conveyorBelt_green.conveyForward();
            } else if (gamepad1.right_trigger > 0) {
                conveyorBelt_green.conveyBackward();
            } else {
                conveyorBelt_green.stopConveying();
            }

            telemetry.addLine();

            if (gamepad1.a) {
                intake.startIntaking();
            } else {
                intake.stopIntaking();
            }

            if (gamepad1.dpadUpWasPressed()) {
                launcher_purple.launchSpeedIncreasing();
                launcher_green.launchSpeedIncreasing();
            }

            if (gamepad1.dpadDownWasPressed()) {
                launcher_purple.launchSpeedDecreasing();
                launcher_green.launchSpeedDecreasing();
            }

            telemetry.update();

        }
    }
}