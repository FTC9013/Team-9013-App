package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.Encoder;
import com.pedropathing.revhub.localizers.RevHubIMU;
import com.pedropathing.revhub.localizers.ThreeWheelIMUConfig;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Constants
{
  public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
    c.frontLeftName.set("leftFront");
    c.frontRightName.set("rightFront");
    c.backLeftName.set("leftRear");
    c.backRightName.set("rightRear");
    c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
    c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
    c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
    c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
  });
  public static ThreeWheelIMUConfig localizerConfig = new ThreeWheelIMUConfig(c -> {
    c.leftEncoderName.set("leftFront");
    c.rightEncoderName.set("rightFront");
    c.strafeEncoderName.set("rightRear");
    c.imuName.set("imu");
    c.imu.set(new RevHubIMU(new RevHubOrientationOnRobot(
      RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
      RevHubOrientationOnRobot.UsbFacingDirection.UP
    )));
    c.leftPodY.set(-0.0);
    c.rightPodY.set(-169.63987543058613);
    c.strafePodX.set(-112.42275019642435);
    c.forwardTicksToInches.set(0.06539133575565856);
    c.strafeTicksToInches.set(0.0020781020001731754);
    c.turnTicksToRadians.set(0.06319314476891175);
    c.leftEncoderDirection.set(Encoder.FORWARD);
    c.rightEncoderDirection.set(Encoder.FORWARD);
    c.strafeEncoderDirection.set(Encoder.FORWARD);
  });
}