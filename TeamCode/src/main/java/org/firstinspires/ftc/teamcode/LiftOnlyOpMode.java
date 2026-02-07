package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Lift only", group = "Linear Opmode")
public class LiftOnlyOpMode extends LinearOpMode
{
  
  //public ConveyorBelt conveyorForward;
  //public ConveyorBelt conveyorBackward;
  
  @Override
  public void runOpMode() throws InterruptedException
  {
    //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    
    Lift lift = new Lift(hardwareMap, telemetry);
    
    
    while (opModeIsActive())
    
    {
      
      if (gamepad1.b || gamepad1.x)
      {
        if (gamepad1.x && gamepad1.b)
        {
          lift.liftUpRight();
          lift.liftUpLeft();
          telemetry.addLine("lifting both yo");
        } else if (gamepad1.b)
        {
          lift.liftUpRight();
          telemetry.addLine("lifting right yo");
        } else if (gamepad1.x)
        {
          lift.liftUpLeft();
          telemetry.addLine("lifting left yo");
        }
      } else if (gamepad1.left_stick_y > 0.5 || gamepad1.right_stick_y > 0.5)
      {
        if (gamepad1.left_stick_y > 0.5 && gamepad1.right_stick_y > 0.5)
        {
          lift.liftDown();
          telemetry.addLine("lifting down yo");
        } else if (gamepad1.left_stick_y > 0.5)
        {
          lift.liftDownLeft();
          telemetry.addLine("lifting down Left yo");
        } else if (gamepad1.right_stick_y > 0.5)
        {
          lift.liftDownRight();
          telemetry.addLine("lifting down Right yo");
        }
      } else
      {
        lift.stop();
        telemetry.addLine("NOT lifting yo");
      }
    }
    
  }
  
}
