package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Arm Test")
public class ArmTest extends LinearOpMode
{
  public ArmControl arm;
  
  @Override
  public void runOpMode()
  {
    arm = new ArmControl(hardwareMap, telemetry);
    waitForStart();
    arm.fullExtend();
    telemetry.addLine("Arm extended");
    telemetry.update();
    sleep(1000);
    arm.reset();
    telemetry.addLine("Arm reset");
    telemetry.update();
    sleep(1000);
    arm.moveArmTo(100);
    telemetry.addLine("Arm moved 100");
    telemetry.update();
    sleep(1000);
    arm.moveArmTo(0);
    telemetry.addLine("Arm moved 0");
    telemetry.update();
    sleep(1000);
    arm.openGripper();
    telemetry.addLine("Toggle gripper1");
    telemetry.update();
    sleep(1000);
    arm.closeGripper();
    telemetry.addLine("Toggle gripper2");
    telemetry.update();
    sleep(1000);
    arm.openGripper();
    telemetry.addLine("Toggle gripper3");
    telemetry.update();
    sleep(1000);
  }
  
  
}
