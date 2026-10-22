package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

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

//  public static ThreeWheelIMUConfig localizerConfig = new ThreeWheelIMUConfig(c -> {
//    c.leftEncoderName.set("rightRear");
//    c.rightEncoderName.set("rightFront");
//    c.strafeEncoderName.set("leftRear");
//    c.imuName.set("imu");
//    c.imu.set(new RevHubIMU(new RevHubOrientationOnRobot(
//      RevHubOrientationOnRobot.LogoFacingDirection.UP,
//      RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
//    )));
//    c.leftPodY.set(4.0354435727402995);
//    c.rightPodY.set(-3.959267639533125);
//    c.strafePodX.set(-1.5807891983235547);
//    c.forwardTicksToInches.set(0.0029544907672097616);
//    c.strafeTicksToInches.set(0.002891551881823767);
//    c.turnTicksToRadians.set(0.0028948969585120125);
//    c.leftEncoderDirection.set(Encoder.REVERSE);
//    c.rightEncoderDirection.set(Encoder.FORWARD);
//    c.strafeEncoderDirection.set(Encoder.REVERSE);
//  });
  
  public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
    c.name.set("pinpointOdometry");
    c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
    c.xPodOffset.set(-6.5);
    c.yPodOffset.set(4.5);
    c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    c.globalDistanceUnit.set(DistanceUnit.INCH);
    c.offsetUnits.set(DistanceUnit.INCH);
  });
  public static ForesightConfig foresightConfig = new ForesightConfig(
    c -> {
      Controller primaryTranslationalForward = Controller.proportional(0.20532801673272555);
      Controller secondaryTranslationalForward = Controller.proportional(0.0758632157170893);
      Controller primaryTranslationalLateral = Controller.proportional(0.2642689025691345);
      Controller secondaryTranslationalLateral = Controller.proportional(0.09764029810416697);
      
      c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
      c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));
      
      c.coast.set(Controller.proportionalFeedforward(0.017946258470079485));
      c.brake.set(Controller.proportionalFeedforward(0.015254319699567562));
      
      c.headingFeedback.set(Controller.proportional(3.290451437599347));
      c.headingBrakeCoefficients.set(Vector2D.cartesian(0.03968586997972741, 0.006535249979157713));
      
      c.linearBrakeCoefficients.set(Matrix.diag(0.08288376046885178, 0.0605502613865949));
      c.quadraticBrakeCoefficients.set(Matrix.diag(7.628384295352696E-4, 0.0010531519479502812));
      
      c.maxAchievableForwardVelocity.set(59.146201503759485);
      c.maxAchievableStrafeVelocity.set(45.741685400504636);
      c.naturalForwardDeceleration.set(41.43513713422553);
      c.naturalStrafeDeceleration.set(56.11426727933896);
    }
  );
  
  public static Follower create(HardwareMap h)
  {
    return new Follower(
      new PinpointLocalizer(h, localizerConfig),
      new Mecanum(h, drivetrainConfig),
      new Foresight(foresightConfig)
    );
  }
}