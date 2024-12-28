package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmControl
{
  private final DcMotor armMotor;
  private final Servo gripper;
  private final DcMotor extensionMotor;
  //private final TouchSensor limitSwitch;
  private final Telemetry telemetry;
  public TouchSensor bottomTouchSensor;
  public TouchSensor topTouchSensor;
  static final double ARM_SPEED = 0.8;
  static final double EXTENSION_SPEED = 1;
  static final int MAX_EXTENSION = 2500;
  private final ElapsedTime runtime = new ElapsedTime();
  private boolean movingUp = false;
  
  
  ArmControl(HardwareMap hardwareMap, Telemetry theTelemetry)
  
  {
    telemetry = theTelemetry;
    // Initialize the hardware variables
    armMotor = hardwareMap.get(DcMotor.class, "arm");
    gripper = hardwareMap.get(Servo.class, "gripper");
    bottomTouchSensor = hardwareMap.get(TouchSensor.class, "arm_failsafe_bottom");
    topTouchSensor = hardwareMap.get(TouchSensor.class, "arm_failsafe_top");
    extensionMotor = hardwareMap.get(DcMotor.class, "extension_motor");
    gripper.setPosition(0);
    //limitSwitch = hardwareMap.get(TouchSensor.class, "limitSwitch");
    armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    //extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.);
    // Motors on one side reversed to drive forward
    // Reverse the motor that runs backwards when connected directly to the battery
    // A positive power number should drive the robot forward regardless of the motor's position on the robot.
    armMotor.setDirection(DcMotor.Direction.FORWARD);
    armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    //extensionMotor.setDirection(DcMotor.Direction.________);
    extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    extensionMotor.setDirection(DcMotor.Direction.REVERSE);
  }
  
  
  public void reset()
  {
    armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    armMotor.setPower(-0.9);
    telemetry.addLine("Resetting arm");
    telemetry.update();
    runtime.reset();
    while (!bottomTouchSensor.isPressed() && runtime.seconds() < 6)
    {
    }
    armMotor.setPower(0);
    armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }
  
  public void resetTelop()
  {
    armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }
  
  public void resetAuto()
  {
    moveArmTo(2500);
    armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
  }
  
  public void raiseMax()
  {
    startMovingTo(4400);
    waitUntilDone();
    /*
    armMotor.setPower(0.9);
    telemetry.addLine("Resetting arm");
    telemetry.addData("Before raising position:", armMotor.getCurrentPosition());
    telemetry.update();
    while (!topTouchSensor.isPressed())
    {
    }
    armMotor.setPower(0);
    armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    telemetry.addData("After raising position:", armMotor.getCurrentPosition());
    telemetry.update();
    */
    
  }
  
  
  public void moveArmTo(int distance)
  {
    startMovingTo(distance);
    telemetry.addData("Before raising position:", armMotor.getCurrentPosition());
    telemetry.addData("Distance: ", distance);
    waitUntilDone();
    telemetry.addData("After raising position:", armMotor.getCurrentPosition());
    telemetry.update();
    
  }
  
  public void releaseBrake()
  {
    extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    extensionMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    extensionMotor.setPower(-0.5);
  }
  
  public void startMovingTo(int distance)
  {
    telemetry.addLine("Raising the G R I P P E R arm");
    telemetry.addData("Current arm pos=", armMotor.getCurrentPosition());
    telemetry.addData("Desired arm distance=", distance);
    
    
    int armPosition = armMotor.getCurrentPosition();
    armMotor.setTargetPosition(distance);
    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    if (armPosition > distance)
    {
      movingUp = false;
      telemetry.addLine("Arm goin down");
      armMotor.setPower(-ARM_SPEED);
      
    } else
    {
      movingUp = true;
      telemetry.addLine("arm goin up");
      armMotor.setPower(ARM_SPEED);
      
    }
    
    telemetry.update();
  }
  
  public void waitUntilDone()
  {
    telemetry.addData("Moving up = ", movingUp);
    runtime.reset();
    while (armMotor.isBusy() && runtime.seconds() < 5)
    {
      if (movingUp && topTouchSensor.isPressed())
      {
        telemetry.addLine("Reach limit of debt");
        break;
      } else if (!movingUp && bottomTouchSensor.isPressed())
      {
        telemetry.addLine("Reach the lowest of depths");
        break;
      }
    }
    stop();
  }
  
  public void stop()
  {
    
    //telemetry.addData("Limit Switch?", limitSwitch.isPressed() ? "Pressed" : "Not Pressed");
    armMotor.setPower(0);
    extensionMotor.setPower(0);
    if (bottomTouchSensor.isPressed())
    {
      telemetry.addLine("bottom touch sensor is pressed");
    } else if (topTouchSensor.isPressed())
    {
      telemetry.addLine("top touch sensor is pressed");
    } else
    {
      telemetry.addLine("no button pressed :(");
    }
  }
  
  public void openGripper()
  {
    gripper.setPosition(1);
  }
  
  public void closeGripper()
  {
    gripper.setPosition(0);
  }
  
  public void fullExtend()
  {
    extendTo(3000);
  }
  
  public void smallExtend()
  {
    extendTo(100);
  }
  
  public void extendMax()
  {
    extendTo(1483 - extensionMotor.getCurrentPosition());
  }
  
  public void extendTo(int distance)
  {
    telemetry.addLine("Extending the arm");
    telemetry.update();
    extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    extensionMotor.setTargetPosition(distance);
    extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    extensionMotor.setPower(EXTENSION_SPEED);
    runtime.reset();
    while (extensionMotor.isBusy() && runtime.seconds() < 3)
    {
      //look into the void of nothingness and dispare
    }
    extensionMotor.setPower(0);
    
  }
  
  public void stopExtending()
  {
    extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    extensionMotor.setPower(0);
  }
  
  public void retract()
  {
    extensionMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    extensionMotor.setPower(-0.5);
  }
  
  public void extend()
  {
    extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    extensionMotor.setPower(EXTENSION_SPEED);
    
  }
  
  public void lower()
  {
    if (!bottomTouchSensor.isPressed())
    {
      armMotor.setPower(-ARM_SPEED);
      telemetry.addLine("Going down in the arm");
    } else
    {
      telemetry.addLine("Stopping the arm");
      stop();
    }
    telemetry.addData("Arm Position: ", armMotor.getCurrentPosition());
  }
  
  public void raise()
  {
    if (!topTouchSensor.isPressed())
    {
      armMotor.setPower(ARM_SPEED);
      telemetry.addLine("Going up in the arm");
    } else
    {
      telemetry.addLine("Stopping the arm");
      stop();
    }
    telemetry.addData("Arm Position: ", armMotor.getCurrentPosition());
  }
  
  public void printSensors()
  {
    telemetry.addData("Arm Position: ", armMotor.getCurrentPosition());
    telemetry.addData("Extension Position: ", extensionMotor.getCurrentPosition());
  }
  
  public void extendForTime(double time)
  {
    telemetry.addLine("Extending the arm");
    telemetry.update();
    extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    extensionMotor.setPower(EXTENSION_SPEED);
    runtime.reset();
    while (runtime.seconds() < time)
    {
      //look into the void of nothingness and dispare
    }
    extensionMotor.setPower(0);
  }
}

