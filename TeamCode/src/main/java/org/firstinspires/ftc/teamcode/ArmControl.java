package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

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
  static final double ARM_SPEED = 0.6;
  static final double EXTENSION_SPEED = 1;
  
  
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
    extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    // Motors on one side reversed to drive forward
    // Reverse the motor that runs backwards when connected directly to the battery
    // A positive power number should drive the robot forward regardless of the motor's position on the robot.
    armMotor.setDirection(DcMotor.Direction.FORWARD);
    armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    //extensionMotor.setDirection(DcMotor.Direction.________);
    extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    extensionMotor.setDirection(DcMotor.Direction.REVERSE);
  }
  
  
  public void reset()
  {
    armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    armMotor.setPower(-ARM_SPEED);
    while (!bottomTouchSensor.isPressed())
    {
      telemetry.addLine("Resetting arm");
    }
    armMotor.setPower(0);
    armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }
  
  public void moveArmTo(int distance)
  {
    startMovingTo(distance);
    waitUntilDone();
    
    
  }
  
  public void startMovingTo(int distance)
  {
    telemetry.addLine("Raising the G R I P P E R arm");
    telemetry.update();
    int armPosition = armMotor.getCurrentPosition();
    armMotor.setTargetPosition(distance);
    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    if (armPosition > distance)
    {
      armMotor.setPower(ARM_SPEED);
      
    } else
    {
      armMotor.setPower(ARM_SPEED);
      
    }
  }
  
  public void waitUntilDone()
  {
    boolean movingUp = armMotor.getPower() > 0;
    while (armMotor.isBusy())
    {
      if (movingUp && topTouchSensor.isPressed())
      {
        break;
      } else if (!movingUp && bottomTouchSensor.isPressed())
      {
        break;
      }
    }
    stop();
  }
  
  public void stop()
  {
    
    //telemetry.addData("Limit Switch?", limitSwitch.isPressed() ? "Pressed" : "Not Pressed");
    armMotor.setPower(0);
    if (bottomTouchSensor.isPressed())
    {
      telemetry.addLine("bottem touch sensor is pressed");
    } else if (topTouchSensor.isPressed())
    {
      telemetry.addLine("top touch sensor is pressed");
    } else
    {
      telemetry.addLine("no botoon pressed :(");
    }
  }
  
  public void openGripper()
  {
    gripper.setPosition(1);
  }
  
  public void closeGripper()
  {
    gripper.setPosition(0.5);
  }
  
  public void fullExtend()
  {
    extend(3000);
  }
  
  public void smallExtend()
  {
    extend(100);
  }
  
  public void extend(int distance)
  {
    telemetry.addLine("Extending the arm");
    telemetry.update();
    extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    extensionMotor.setTargetPosition(distance);
    extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    extensionMotor.setPower(EXTENSION_SPEED);
    while (extensionMotor.isBusy())
    {
      //look into the void of nothingness and dispare
    }
    extensionMotor.setPower(0);
    
  }
  
  public void stopExtending()
  {
    extensionMotor.setPower(0);
  }
  
  public void extending()
  {
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
}
