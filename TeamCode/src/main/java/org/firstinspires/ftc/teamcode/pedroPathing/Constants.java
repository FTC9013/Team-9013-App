package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.ThreeWheelIMUConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Constants
{
  public static ThreeWheelIMUConstants localizerConstants = new ThreeWheelIMUConstants()
    .forwardTicksToInches(.001989436789)
    .strafeTicksToInches(.001989436789)
    .turnTicksToInches(.001989436789)
    .leftPodY(4.25)
    .rightPodY(-4.25)
    .strafePodX(-3.25)
    .leftEncoder_HardwareMapName("leftFront")
    .rightEncoder_HardwareMapName("rightFront")
    .strafeEncoder_HardwareMapName("rightRear")
    .leftEncoderDirection(Encoder.FORWARD)
    .rightEncoderDirection(Encoder.FORWARD)
    .strafeEncoderDirection(Encoder.FORWARD)
    .IMU_HardwareMapName("imu")
    .IMU_Orientation(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
  public static FollowerConstants followerConstants = new FollowerConstants()
    .mass(5.443);
  public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);
  
  public static Follower createFollower(HardwareMap hardwareMap)
  {
    return new FollowerBuilder(followerConstants, hardwareMap)
      .threeWheelIMULocalizer(localizerConstants)
      .pathConstraints(pathConstraints)
      .mecanumDrivetrain(driveConstants)
      .build();
  }
  
  public static MecanumConstants driveConstants = new MecanumConstants()
    .maxPower(1)
    .rightFrontMotorName("rightFront")
    .rightRearMotorName("rightRear")
    .leftRearMotorName("leftRear")
    .leftFrontMotorName("leftFront")
    .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
    .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
    .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
    .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD);
}
