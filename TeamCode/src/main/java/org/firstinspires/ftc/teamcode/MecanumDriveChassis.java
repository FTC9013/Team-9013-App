package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MecanumDriveChassis

{
  IMU imu;
  
  double actualSpeed;
  double tickPerCm = 17.7914; //17.7914
  double autonomousPower = 1.0;
  double strafeAutonomousPower = 0.4;
  int turnDistance = 843;
  int turnDistanceYaw = 870;
  private final DcMotor leftFrontDrive;
  private final DcMotor leftRearDrive;
  private final DcMotor rightFrontDrive;
  private final DcMotor rightRearDrive;
  
  private static double leftFrontDriveSpeed;
  private static double leftRearDriveSpeed;
  private static double rightFrontDriveSpeed;
  private static double rightRearDriveSpeed;
  // Robot speed [-1, 1].  (speed in any direction that is not rotational)
  // does not have any angular component, just scaler velocity.
  // combined with the angular component for motion.  Even if angle is 0 (forward).
  private static double inputSpeed = 0;
  // Robot angle while moving [0, 2PI] or [0, +/-PI]. (angle to displace the center of the bot,
  // ASDF)
  // relative to the direction the bot is facing.
  private static double thetaD = 0;
  // Speed component for rotation about the Z axis. [-x, x]
  // controlled by the error signal from the heading PID
  private static double rotationalSpeed = 0;
  // Robot speed scaling factor (% of joystick input to use)
  // applied uniformly across all joystick inputs to the JoystickToMotion() method.
  // number of encoder counts equal to one inch of forward travel
  //  private final int countsPerDriveInch = 5000/117;
  // number of encoder counts equal to one inch of forward travel
  //  private  final int countsPerStrafeInch = 5000/51;
  private final Telemetry telemetry;
  
  MecanumDriveChassis(HardwareMap hardwareMap, Telemetry theTelemetry)
  {
    telemetry = theTelemetry;
    
    // Initialize the hardware variables. Note that the strings used here as parameters
    // to 'get' must correspond to the names assigned during the robot configuration
    // step (using the FTC Robot Controller app on the phone).
    
    imu = hardwareMap.get(IMU.class, "imu");
    
    RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
    RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
    
    RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
    
    // Now initialize the IMU with this mounting orientation
    // Note: if you choose two conflicting directions, this initialization will cause a code exception.
    imu.initialize(new IMU.Parameters(orientationOnRobot));
    imu.resetYaw();
    
    YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
    orientation.getYaw(AngleUnit.DEGREES);
    
    leftFrontDrive = hardwareMap.get(DcMotor.class, "lFront");
    leftRearDrive = hardwareMap.get(DcMotor.class, "lRear");
    rightFrontDrive = hardwareMap.get(DcMotor.class, "rFront");
    rightRearDrive = hardwareMap.get(DcMotor.class, "rRear");
    
    leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftRearDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rightRearDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    RunWithoutEncoders();
    // Motors on one side reversed to drive forward
    // Reverse the motor that runs backwards when connected directly to the battery
    // A positive power number should drive the robot forward regardless of the motor's
    // position on the robot.
    leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
    leftRearDrive.setDirection(DcMotor.Direction.REVERSE);
    rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
    rightRearDrive.setDirection(DcMotor.Direction.FORWARD);
    // set motion parameters.
    inputSpeed = 0;
    thetaD = 0;
    rotationalSpeed = 0;
    // Set all the motor speeds.
    rightFrontDriveSpeed = 0;
    leftFrontDriveSpeed = 0;
    rightRearDriveSpeed = 0;
    leftRearDriveSpeed = 0;
    
    rightFrontDrive.setPower(rightFrontDriveSpeed);
    leftFrontDrive.setPower(leftFrontDriveSpeed);
    rightRearDrive.setPower(rightRearDriveSpeed);
    leftRearDrive.setPower(leftRearDriveSpeed);
    // create and initialize the PID for the heading
    // PID for the heading
    double propCoeff = 0.9;
    double integCoeff = 0.0;
    double diffCoeff = 0.00;
    PID headingPID = new PID(propCoeff, integCoeff, diffCoeff);
    // set initial desired heading to the current actual heading.
    // heading about a unit circle in radians.
    // rotates about the Z axis [0,2PI) rad.
    // initially setup the PID parameters
    double outputLowLimit = -1;
    double outputHighLimit = 1;
    headingPID.setOutputLimits(outputLowLimit, outputHighLimit);
    double maxIOutput = 1;
    headingPID.setMaxIOutput(maxIOutput);
    double outputRampRate = 0.1;
    headingPID.setOutputRampRate(outputRampRate);
    double outputFilter = 0;
    headingPID.setOutputFilter(outputFilter);
    double setpointRange = 2 * Math.PI;
    headingPID.setSetpointRange(setpointRange);
    headingPID.setContinousInputRange(2 * Math.PI);
    headingPID.setContinous(true);  // lets PID know we are working with a continuous range [0-360)
  }
  
  /**
   void testWheels()
   {
   try
   {
   rightRearDrive.setPower(0.3);
   telemetry.addLine("Spinning right rear");
   telemetry.update();
   Thread.sleep(5000);
   rightRearDrive.setPower(0);
   
   rightFrontDrive.setPower(0.3);
   telemetry.addLine("Spinning right front");
   telemetry.update();
   Thread.sleep(5000);
   rightFrontDrive.setPower(0);
   
   leftRearDrive.setPower(0.3);
   telemetry.addLine("Spinning left rear");
   telemetry.update();
   Thread.sleep(5000);
   leftRearDrive.setPower(0);
   
   leftFrontDrive.setPower(0.3);
   telemetry.addLine("Spinning left front");
   telemetry.update();
   Thread.sleep(5000);
   leftFrontDrive.setPower(0);
   } catch (InterruptedException e)
   {
   }
   }
   **/
  // Left  Y = forward, backward movement
  // Left  X = side to side (strafe)
  // Right X = rotate in place
  void drive(float driveLeftY, float driveLeftX, float driveRightX, boolean goFast)
  {
    // calculate the vectors multiply input values by scaling factor for max speed.
    joystickToMotion(driveLeftY, driveLeftX, driveRightX);
    
    if (!goFast)
    {
      inputSpeed = (.425 * inputSpeed);
      rotationalSpeed = (.375 * rotationalSpeed);
      telemetry.addData("goFast: ", "off");
    } else
    {
      inputSpeed = (0.9 * inputSpeed);
      rotationalSpeed = (.45 * rotationalSpeed);
    }
    
    telemetry.addData("Theta(Degrees)", thetaD);
    telemetry.addData("inputSpeed", inputSpeed);
    // Math out what to send to the motors and send it.
    PowerToWheels();
  }
  
  private void joystickToMotion(double directionY, double directionX, double rotationX)
  {
    // determines the translation speed by taking the hypotenuse of the vector created by
    // the X & Y components.
    inputSpeed = Math.min(Math.sqrt(Math.pow(directionX, 2) + Math.pow(-directionY, 2)), 1);
    // Converts the joystick inputs from cartesian to polar from 0 to +/- PI oriented
    // with 0 to the right of the robot. (standard polar plot)
    thetaD = Math.atan2(-directionY, directionX);
    // orient to the robot by rotating PI/2 to make the joystick zero at the forward of bot.
    // instead of the right side.
    //thetaD = thetaD - Math.PI / 2;
    // simply takes the right stick X value and invert to use as a rotational speed.
    // inverted since we want CW rotation on a positive value.
    // which is opposite of what PowerToWheels() wants in polar positive rotation (CCW).
    rotationalSpeed = rotationX;
  }
  
  private void PowerToWheels()
  {
    actualSpeed += (inputSpeed - actualSpeed) * 0.2;
    telemetry.addData("Actual Speed:", actualSpeed);
    double currentLeftFrontSpeed = leftFrontDriveSpeed;
    double currentLeftRearSpeed = leftRearDriveSpeed;
    double currentRightFrontSpeed = rightFrontDriveSpeed;
    double currentRightRearSpeed = rightRearDriveSpeed;
    if (inputSpeed < .03)
    {
      actualSpeed = 0;
      leftFrontDriveSpeed = 0;
      rightFrontDriveSpeed = 0;
      leftRearDriveSpeed = 0;
      rightRearDriveSpeed = 0;
      telemetry.addData("Expected Direction: ", "Stopped");
    } else if (thetaD >= (Math.PI * -2) / 8 && thetaD < (Math.PI * 2 / 8))
    {
      leftFrontDriveSpeed = actualSpeed;
      rightFrontDriveSpeed = -actualSpeed;
      leftRearDriveSpeed = -actualSpeed;
      rightRearDriveSpeed = actualSpeed;
      telemetry.addData("Expected Direction: ", "Right");
    } else if (thetaD >= (Math.PI * 2 / 8) && thetaD < (Math.PI * 6 / 8))
    {
      leftFrontDriveSpeed = actualSpeed;
      rightFrontDriveSpeed = actualSpeed;
      leftRearDriveSpeed = actualSpeed;
      rightRearDriveSpeed = actualSpeed;
      telemetry.addData("Expected Direction: ", "Up");
    } else if (thetaD >= (Math.PI * 6 / 8) || thetaD < Math.PI * -6 / 8)
    {
      leftFrontDriveSpeed = -actualSpeed;
      rightFrontDriveSpeed = actualSpeed;
      leftRearDriveSpeed = actualSpeed;
      rightRearDriveSpeed = -actualSpeed;
      telemetry.addData("Expected Direction: ", "Left");
    } else if (thetaD >= (Math.PI * -6 / 8) && thetaD < (Math.PI * -2 / 8))
    {
      leftFrontDriveSpeed = -actualSpeed;
      rightFrontDriveSpeed = -actualSpeed;
      leftRearDriveSpeed = -actualSpeed;
      rightRearDriveSpeed = -actualSpeed;
      telemetry.addData("Expected Direction: ", "Down");
    } else
    {
      leftFrontDriveSpeed = 0;
      rightFrontDriveSpeed = 0;
      leftRearDriveSpeed = 0;
      rightRearDriveSpeed = 0;
    }
    
    leftFrontDriveSpeed += rotationalSpeed;
    rightFrontDriveSpeed -= rotationalSpeed;
    leftRearDriveSpeed += rotationalSpeed;
    rightRearDriveSpeed -= rotationalSpeed;
    // place all the power numbers in a list for collection manipulations
    // (easier to find min / max etc when in a list)
    List<Double> speeds = Arrays.asList(rightFrontDriveSpeed,
      leftFrontDriveSpeed, rightRearDriveSpeed, leftRearDriveSpeed);
    // scales the motor powers while maintaining power ratios.
    double minPower = Collections.min(speeds);
    double maxPower = Collections.max(speeds);
    double maxMag = Math.max(Math.abs(minPower), Math.abs(maxPower));
    if (maxMag > 1.0)
    {
      for (int i = 0; i < speeds.size(); i++)
      {
        speeds.set(i, speeds.get(i) / maxMag);
      }
    }
    try
    {
      if (currentRightRearSpeed > 0 && rightRearDriveSpeed < 0)
      {
        rightRearDrive.setPower(0);
        Thread.sleep(5);
      }
      
      if (currentRightRearSpeed < 0 && rightRearDriveSpeed > 0)
      {
        rightRearDrive.setPower(0);
        Thread.sleep(5);
      }
      
      if (currentLeftRearSpeed > 0 && leftRearDriveSpeed < 0)
      {
        leftRearDrive.setPower(0);
        Thread.sleep(5);
      }
      
      if (currentLeftRearSpeed < 0 && leftRearDriveSpeed > 0)
      {
        leftRearDrive.setPower(0);
        Thread.sleep(5);
      }
      
      if (currentLeftFrontSpeed > 0 && leftFrontDriveSpeed < 0)
      {
        leftFrontDrive.setPower(0);
        Thread.sleep(5);
      }
      
      if (currentLeftFrontSpeed < 0 && leftFrontDriveSpeed > 0)
      {
        leftFrontDrive.setPower(0);
        Thread.sleep(5);
      }
      
      if (currentRightFrontSpeed > 0 && rightFrontDriveSpeed < 0)
      {
        rightFrontDrive.setPower(0);
        Thread.sleep(5);
      }
      
      if (currentRightFrontSpeed < 0 && rightFrontDriveSpeed > 0)
      {
        rightFrontDrive.setPower(0);
        Thread.sleep(5);
      }
    } catch (InterruptedException e)
    {
    }
    // must be same order as placed in the list
    // send the speeds to the motors
    rightFrontDrive.setPower(speeds.get(0));
    leftFrontDrive.setPower(speeds.get(1));
    rightRearDrive.setPower(speeds.get(3));
    leftRearDrive.setPower(speeds.get(2));
  }
  
  void RunWithoutEncoders()
  {
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
  }
  
  public void moveForward(double distanceCm)
  {
    
    telemetry.addLine("moving forward");
    
    startMovingForward(distanceCm);
    while (stillMoving())
    {
      
      //Do nothing. Allows the motors to spin
    }
    stop_motors();
  }
  
  public void startMovingForward(double distanceCm)
  {
    startMovingForward(distanceCm, autonomousPower);
  }
  
  public void startMovingForward(double distanceCm, double power)
  {
    
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    
    
    leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    int distance = (int) (distanceCm * tickPerCm);
    
    leftFrontDrive.setTargetPosition(distance);
    rightFrontDrive.setTargetPosition(distance);
    leftRearDrive.setTargetPosition(distance);
    rightRearDrive.setTargetPosition(distance);
    
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    leftFrontDrive.setPower(autonomousPower);
    leftRearDrive.setPower(autonomousPower);
    rightFrontDrive.setPower(autonomousPower);
    rightRearDrive.setPower(autonomousPower);
  }
  
  public boolean stillMoving()
  {
    return leftFrontDrive.isBusy() && rightFrontDrive.isBusy() && leftRearDrive.isBusy() && rightRearDrive.isBusy();
    
  }
  
  public void moveBackward(double distanceCm)
  {
    moveBackward(distanceCm, autonomousPower);
  }
  
  public void moveBackward(double distanceCm, double speed)
  {
    
    telemetry.addLine("moving backward");
    telemetry.update();
    
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    
    
    leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    int distance = (int) (distanceCm * tickPerCm);
    
    leftFrontDrive.setTargetPosition(-distance);
    rightFrontDrive.setTargetPosition(-distance);
    leftRearDrive.setTargetPosition(-distance);
    rightRearDrive.setTargetPosition(-distance);
    
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    leftFrontDrive.setPower(-speed);
    leftRearDrive.setPower(-speed);
    rightFrontDrive.setPower(-speed);
    rightRearDrive.setPower(-speed);
    while (leftFrontDrive.isBusy() && rightFrontDrive.isBusy() && leftRearDrive.isBusy() && rightRearDrive.isBusy())
    {
      //Do nothing. Allows the motors to spin
    }
    stop_motors();
  }
  
  public void strafeLeft(double distanceCm)
  {
    telemetry.addLine("strafing left");
    telemetry.update();
    
    int distance = (int) (distanceCm * tickPerCm);
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    
    
    leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    
    leftFrontDrive.setTargetPosition(-distance);
    rightFrontDrive.setTargetPosition(distance);
    leftRearDrive.setTargetPosition(distance);
    rightRearDrive.setTargetPosition(-distance);
    
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    leftFrontDrive.setPower(-strafeAutonomousPower);
    rightFrontDrive.setPower(strafeAutonomousPower);
    leftRearDrive.setPower(strafeAutonomousPower);
    rightRearDrive.setPower(-strafeAutonomousPower);
    while (leftFrontDrive.isBusy() && rightFrontDrive.isBusy() && leftRearDrive.isBusy() && rightRearDrive.isBusy())
    {
      //Do nothing. Allows the motors to spin
    }
    stop_motors();
  }
  
  public void strafeLeftTicks(int distance)
  {
    telemetry.addLine("strafing left");
    telemetry.update();
    
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    
    
    leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    
    leftFrontDrive.setTargetPosition(-distance);
    rightFrontDrive.setTargetPosition(distance);
    leftRearDrive.setTargetPosition(distance);
    rightRearDrive.setTargetPosition(-distance);
    
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    leftFrontDrive.setPower(-autonomousPower);
    rightFrontDrive.setPower(autonomousPower);
    leftRearDrive.setPower(autonomousPower);
    rightRearDrive.setPower(-autonomousPower);
    while (leftFrontDrive.isBusy() && rightFrontDrive.isBusy() && leftRearDrive.isBusy() && rightRearDrive.isBusy())
    {
      //Do nothing. Allows the motors to spin
    }
    stop_motors();
  }
  
  
  public void strafeRight(double distanceCm)
  {
    telemetry.addLine("strafing right");
    telemetry.update();
    int distance = (int) (distanceCm * tickPerCm);
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    
    
    leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    
    leftFrontDrive.setTargetPosition(distance);
    rightFrontDrive.setTargetPosition(-distance);
    leftRearDrive.setTargetPosition(-distance);
    rightRearDrive.setTargetPosition(distance);
    
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    leftFrontDrive.setPower(strafeAutonomousPower);
    rightFrontDrive.setPower(-strafeAutonomousPower);
    leftRearDrive.setPower(-strafeAutonomousPower);
    rightRearDrive.setPower(strafeAutonomousPower);
    while (leftFrontDrive.isBusy() && rightFrontDrive.isBusy() && leftRearDrive.isBusy() && rightRearDrive.isBusy())
    {
      //Do nothing. Allows the motors to spin
    }
    stop_motors();
  }
  
  public void stop_motors()
  {
    rightFrontDrive.setPower(0);
    rightRearDrive.setPower(0);
    leftFrontDrive.setPower(0);
    leftRearDrive.setPower(0);
  }
  
  public void turnLeft()
  {
    turnLeftDistance(turnDistance);
  }
  
  public void turnLeftDistance(int turnDistance)
  {
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    
    
    leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    
    leftFrontDrive.setTargetPosition(-turnDistance);
    rightFrontDrive.setTargetPosition(turnDistance);
    leftRearDrive.setTargetPosition(-turnDistance);
    rightRearDrive.setTargetPosition(turnDistance);
    
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    leftFrontDrive.setPower(-autonomousPower);
    rightFrontDrive.setPower(autonomousPower);
    leftRearDrive.setPower(-autonomousPower);
    rightRearDrive.setPower(autonomousPower);
    while (leftFrontDrive.isBusy() && rightFrontDrive.isBusy() && leftRearDrive.isBusy() && rightRearDrive.isBusy())
    {
      telemetry.addData("Left resr position", leftRearDrive.getCurrentPosition());
      telemetry.addData("Left frunt position", leftFrontDrive.getCurrentPosition());
      telemetry.addData("Right resr position", rightRearDrive.getCurrentPosition());
      telemetry.addData("Right frunt position", rightFrontDrive.getCurrentPosition());
      telemetry.update();
      //Do nothing. Allows the motors to spin
    }
    stop_motors();
  }
  
  
  public void turnRight()
  {
    turnRightDistance(turnDistance);
  }
  
  public void turnRightDistance(int turnDistance)
  {
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    
    
    leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    
    leftFrontDrive.setTargetPosition(turnDistance);
    rightFrontDrive.setTargetPosition(-turnDistance);
    leftRearDrive.setTargetPosition(turnDistance);
    rightRearDrive.setTargetPosition(-turnDistance);
    
    leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    leftRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rightRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    leftFrontDrive.setPower(autonomousPower);
    rightFrontDrive.setPower(-autonomousPower);
    leftRearDrive.setPower(autonomousPower);
    rightRearDrive.setPower(-autonomousPower);
    while (leftFrontDrive.isBusy() && rightFrontDrive.isBusy() && leftRearDrive.isBusy() && rightRearDrive.isBusy())
    {
      //Do nothing. Allows the motors to spin
    }
    stop_motors();
    
  }
  
  public void straighten(double desiredYaw)
  {
    double yaw;
    YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
    int count = 0;
    do
    {
      double ticksPerDegree = turnDistanceYaw / 90.0;
      orientation = imu.getRobotYawPitchRollAngles();
      yaw = orientation.getYaw(AngleUnit.DEGREES);
      double changedYaw = yaw - desiredYaw;
      int turnYawTicks = (int) (changedYaw * ticksPerDegree);
      telemetry.addData("Yaw is:", yaw);
      telemetry.update();
      if (changedYaw > 0 && changedYaw <= 90)
      {
        turnRightDistance(turnYawTicks);
      }
      if (changedYaw < 0 && changedYaw >= -90)
      {
        turnLeftDistance(-turnYawTicks);
      }
      count += 1;
      
    } while (Math.abs(yaw - desiredYaw) > 0.5 && count < 3);
    /*if (changedYaw > 0 && changedYaw <= 180)
    {
      turnRightDistance(turnYawTicks);
    }
    if (changedYaw < 0 && changedYaw >= -180)
    {
      turnLeftDistance(-turnYawTicks);
    }*/
    orientation = imu.getRobotYawPitchRollAngles();
    yaw = orientation.getYaw(AngleUnit.DEGREES);
    telemetry.addData("Yaw is changing to:", desiredYaw);
    telemetry.addData("Yaw is now:", yaw);
    telemetry.update();
  }
}