package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.Encoder;
import com.pedropathing.revhub.localizers.RevHubIMU;
import com.pedropathing.revhub.localizers.ThreeWheelIMUConfig;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
    c.leftEncoderName.set("rightRear");
    c.rightEncoderName.set("rightFront");
    c.strafeEncoderName.set("leftRear");
    c.imuName.set("imu");
    c.imu.set(new RevHubIMU(new RevHubOrientationOnRobot(
      RevHubOrientationOnRobot.LogoFacingDirection.UP,
      RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
    )));
    c.leftPodY.set(3.4562259263278623);
    c.rightPodY.set(-4.933915440183741);
    c.strafePodX.set(-1.523284933800231);
    c.forwardTicksToInches.set(0.003002780414628922);
    c.strafeTicksToInches.set(0.003035176982602847);
    c.turnTicksToRadians.set(0.0030345851346549263);
    c.leftEncoderDirection.set(Encoder.REVERSE);
    c.rightEncoderDirection.set(Encoder.FORWARD);
    c.strafeEncoderDirection.set(Encoder.REVERSE);
  });
  
  public static Follower create(HardwareMap h)
  {
    return null;
  }
}