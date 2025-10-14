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
    public void runOpMode()
    {
        telemetry.addData("Status" , "Initialized");
        conveyorBelt = new ConveyorBelt(hardwareMap, telemetry);
        launcher = new Launcher(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.right_bumper)
            {
                conveyorBelt.startConveying();
            }
            if (gamepad1.left_bumper)
            {
                conveyorBelt.stopConveying();
            }
            if (gamepad1.right_trigger > 0)
            {
                conveyorBelt.startConveyingIncreasing();
                telemetry.addLine("Pressing right trigger");
            }
            if (gamepad1.left_trigger > 0)
            {
                conveyorBelt.startConveyingDecreasing();
                telemetry.addLine("Pressing left trigger");
            }
            if (gamepad1.y)
            {
                launcher.startLaunching();
            }
            else {
                launcher.stopLaunching();
            }
            if(gamepad1.b){
                intake.startIntaking();
            }
            else{
                intake.stopIntaking();
            }
            telemetry.update();

        }
    }
}
//we are working with a motor that does not spin infinitly so,... we   are   cooked
