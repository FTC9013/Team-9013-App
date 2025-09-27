package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.ServoFlavor;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.ServoType;


@TeleOp(name = "Wahooooooooooooooooooooo", group = "Linear Opmode")
public class SpinningMotor extends  LinearOpMode {

    public Servo ServoTest = null;



    //public MecanumDriveChassis ServoTest;
    @Override
    public void runOpMode(){
        ServoTest = hardwareMap.get(Servo.class, "ServoTest");
        waitForStart();
        ServoTest.setDirection(Servo.Direction.REVERSE);
        ServoTest.setPosition(1);
        telemetry.update();
        //while (opModeIsActive()) {
            //ServoTest.moveForward(100);
        //}
    }
}
