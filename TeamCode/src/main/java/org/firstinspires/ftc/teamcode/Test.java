package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Test", group = "Linear Opmode")
public class Test extends LinearOpMode {

    public ConveyorBelt conveyorForward = null;
    public Launcher launcher = null;
    public Intake intake = null;
    public ConveyorBelt conveyorBackward;

    //public MecanumDriveChassis ServoTest;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        conveyorForward = new ConveyorBelt(hardwareMap, telemetry, "forward");
        conveyorBackward = new ConveyorBelt(hardwareMap, telemetry, "backward");
        launcher = new Launcher(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
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

            if (gamepad1.x) {
                intake.stopIntaking();
                launcher.stopLaunching();
                conveyorForward.stopConveying();
                telemetry.addLine("Stopping motors and servo");
            }
            if (gamepad1.y) {
                launcher.startLaunching();
            }


            if (gamepad1.b) {
                intake.startIntaking();
                telemetry.addLine("Pressing key X");
            }

            if (gamepad1.dpad_up) {
                launcher.launchSpeedIncreasing();
            }
            if (gamepad1.dpad_down) {
                launcher.launchSpeedDecreasing();
            }
            telemetry.update();

        }
    }
}

//we are working with a motor that does not spin infinitly so,... we   are   cooked
