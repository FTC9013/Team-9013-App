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
    c.leftEncoderName.set("leftRear");
    c.rightEncoderName.set("rightFront");
    c.strafeEncoderName.set("rightRear");
    c.imuName.set("imu");
    c.imu.set(new RevHubIMU(new RevHubOrientationOnRobot(
      RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
      RevHubOrientationOnRobot.UsbFacingDirection.UP
    )));
    c.leftPodY.set(4.281411240022822);
    c.rightPodY.set(-5.727590613193659);
    c.strafePodX.set(-7.172254705221709);
    c.forwardTicksToInches.set(0.0019507541839960034);
    c.strafeTicksToInches.set(0.0057235017575979616);
    c.turnTicksToRadians.set(0.004226655916638408);
    c.leftEncoderDirection.set(Encoder.FORWARD);
    c.rightEncoderDirection.set(Encoder.FORWARD);
    c.strafeEncoderDirection.set(Encoder.FORWARD);
  });
  public static ForesightConfig foresightConfig = new ForesightConfig(
    c -> {
      Controller primaryTranslationalForward = Controller.proportional(0.23860269541719253);
      Controller secondaryTranslationalForward = Controller.proportional(0.08815732037521032);
      Controller primaryTranslationalLateral = Controller.proportional(1.306053513742182);
      Controller secondaryTranslationalLateral = Controller.proportional(0.4825518749351162);
      
      c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
      c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));
      
      c.coast.set(Controller.proportionalFeedforward(0.019514476218076532));
      c.brake.set(Controller.proportionalFeedforward(0.01658730478536505));
      
      c.headingFeedback.set(Controller.proportional(2.5492294717655244));
      c.headingBrakeCoefficients.set(Vector2D.cartesian(0.06263327453026173, -3.95954430337479E-4));
      
      c.linearBrakeCoefficients.set(Matrix.diag(0.07032279906107308, 0.05483047944161932));
      c.quadraticBrakeCoefficients.set(Matrix.diag(0.0011161586971696628, 2.9694860028321393E-4));
      
      c.maxAchievableForwardVelocity.set(53.49017497440474);
      c.maxAchievableStrafeVelocity.set(117.02521039602331);
      c.naturalForwardDeceleration.set(38.159629208000695);
      c.naturalStrafeDeceleration.set(124.00898481531615);
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