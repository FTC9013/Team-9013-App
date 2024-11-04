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
  public TouchSensor touchSensor;
  
  
  ArmControl(HardwareMap hardwareMap, Telemetry theTelemetry)
  
  {
    telemetry = theTelemetry;
    // Initialize the hardware variables
    armMotor = hardwareMap.get(DcMotor.class, "arm");
    gripper = hardwareMap.get(Servo.class, "gripper");
    touchSensor = hardwareMap.get(TouchSensor.class, "arm_failsafe");
    extensionMotor = hardwareMap.get(DcMotor.class, "extension_motor");
    gripper.setPosition(0);
    //limitSwitch = hardwareMap.get(TouchSensor.class, "limitSwitch");
    armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    // Motors on one side reversed to drive forward
    // Reverse the motor that runs backwards when connected directly to the battery
    // A positive power number should drive the robot forward regardless of the motor's position on the robot.
    armMotor.setDirection(DcMotor.Direction.REVERSE);
    armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    //extensionMotor.setDirection(DcMotor.Direction.________);
    extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }
  
  public void reset()
  {
    armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
  }
  
  public void lowerArmTo(int distance)
  
  {
    
    
    if (!touchSensor.isPressed())
    {
      armMotor.setPower(0.7);
      telemetry.addLine("Goging downe in the G R I P P E R arm");
    } else
    {
      telemetry.addLine("Stoping the G R I P P E R arm");
      armStop();
    }
    
  }
  
  public void moveArmTo(int distance)
  {
    telemetry.addLine("raising the G R I P P E R arm");
    telemetry.update();
    armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armMotor.setTargetPosition(distance);
    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    armMotor.setPower(0.5);
    while (armMotor.isBusy())
    {
      //look into the void of nothingness and dispare
    }
    armMotor.setPower(0);
  }
  
  public void armStop()
  {
    
    //telemetry.addData("Limit Switch?", limitSwitch.isPressed() ? "Pressed" : "Not Pressed");
    armMotor.setPower(0);
  }
  
  public void toggleGripper()
  {
    if (gripper.getPosition() > 0.5)
    {
      gripper.setPosition(0);
    } else if (gripper.getPosition() <= 0.5)
    {
      gripper.setPosition(1);
    }
    telemetry.addData("G R I P P I N G", "True");
  }
  
  public void extendArm()
  {
    telemetry.addLine("Extending the G R I P P E R arm");
    telemetry.update();
    extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    extensionMotor.setTargetPosition(100);
    extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    extensionMotor.setPower(0.5);
    while (extensionMotor.isBusy())
    {
      //look into the void of nothingness and dispare
    }
    extensionMotor.setPower(0);
    
  }
}
