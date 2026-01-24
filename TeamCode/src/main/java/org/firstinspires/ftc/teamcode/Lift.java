package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import androidx.annotation.NonNull;

public class Lift
{
  private DcMotorEx liftMotorLeft;
  private DcMotorEx liftMotorRight;
  private Telemetry telemetry;
  static final int LIFT_DISTANCE = 2500;
  
  Lift(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
  {
    telemetry = theTelemetry;
    liftMotorLeft = hardwareMap.get(DcMotorEx.class, "Lift_Left");
    liftMotorRight = hardwareMap.get(DcMotorEx.class, "Lift_Right");
    liftMotorLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    liftMotorRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    liftMotorLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    liftMotorLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    liftMotorRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    liftMotorRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
  }
  
  public void liftUp()
  {
    liftMotorLeft.setPower(1);
    liftMotorRight.setPower(1);
  }
  
  public void liftDown()
  {
    liftMotorLeft.setPower(-1);
    liftMotorRight.setPower(-1);
  }
  
  public void stopLift()
  {
    liftMotorLeft.setPower(0);
    liftMotorRight.setPower(0);
  }
  
  public void startMovingUp()
  {
    telemetry.addLine("Raising the l i f t arm");
    telemetry.addData("Current arm pos=", liftMotorLeft.getCurrentPosition());
    
    liftMotorLeft.setTargetPosition(LIFT_DISTANCE);
    liftMotorRight.setTargetPosition(LIFT_DISTANCE);
    liftMotorLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    liftMotorRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    
    telemetry.addLine("lift goin down");
    liftMotorLeft.setPower(1);
    liftMotorRight.setPower(1);
  }
  
  public void startMovingDown()
  {
    
    telemetry.addLine("Lowering the l i f t arm");
    telemetry.addData("Current arm pos=", liftMotorLeft.getCurrentPosition());
    
    liftMotorLeft.setTargetPosition(0);
    liftMotorRight.setTargetPosition(0);
    liftMotorLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    liftMotorRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    
    telemetry.addLine("lift goin down");
    liftMotorLeft.setPower(-1);
    liftMotorRight.setPower(-1);
  }
}
