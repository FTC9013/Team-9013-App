package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Test", group = "Linear Opmode")
public class Test extends  LinearOpMode {

    public ConveyorBelt conveyorBelt = null;
    public Launcher launcher = null;
    public Intake intake = null;

    //public MecanumDriveChassis ServoTest;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        conveyorBelt = new ConveyorBelt(hardwareMap, telemetry);
        launcher = new Launcher(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                conveyorBelt.startConveyingIncreasing();
                telemetry.addLine("Pressing right bumper");
            }
            if (gamepad1.left_bumper) {
                conveyorBelt.startConveyingDecreasing();
                telemetry.addLine("Pressing left bumper");
            }
            if (gamepad1.a) {
                conveyorBelt.startConveying();
                telemetry.addLine("Pressing key A");
            }

            if (gamepad1.x) {
                intake.stopIntaking();
                launcher.stopLaunching();
                conveyorBelt.stopConveying();
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
