package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.Encoder;
import com.pedropathing.revhub.localizers.RevHubIMU;
import com.pedropathing.revhub.localizers.ThreeWheelIMUConfig;
import com.pedropathing.revhub.localizers.ThreeWheelIMULocalizer;
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
  
  public static ForesightConfig foresightConfig = new ForesightConfig(
    c -> {
      Controller primaryTranslationalForward = Controller.proportional(0.14576740924157236);
      Controller secondaryTranslationalForward = Controller.proportional(0.05385716273785123);
      Controller primaryTranslationalLateral = Controller.proportional(0.3971349544881233);
      Controller secondaryTranslationalLateral = Controller.proportional(0.14673075404194025);
      
      c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
      c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));
      
      c.coast.set(Controller.proportionalFeedforward(0.016331295716833442));
      c.brake.set(Controller.proportionalFeedforward(0.013881601359308425));
      
      c.headingFeedback.set(Controller.proportional(3.2283937661954076));
      c.headingBrakeCoefficients.set(Vector2D.cartesian(0.05046925315413904, 0.002058118104379805));
      
      c.linearBrakeCoefficients.set(Matrix.diag(0.039679011958373, 0.06268306070304194));
      c.quadraticBrakeCoefficients.set(Matrix.diag(0.0021704383775017636, 0.0012958115316423398));
      
      c.maxAchievableForwardVelocity.set(63.511936828849045);
      c.maxAchievableStrafeVelocity.set(55.78771318075364);
      c.naturalForwardDeceleration.set(32.93583053064561);
      c.naturalStrafeDeceleration.set(48.963718326044386);
    }
  );
  
  public static Follower create(HardwareMap h)
  {
    return new Follower(
      new ThreeWheelIMULocalizer(h, localizerConfig),
      new Mecanum(h, drivetrainConfig),
      new Foresight(foresightConfig)
    );
  }
}