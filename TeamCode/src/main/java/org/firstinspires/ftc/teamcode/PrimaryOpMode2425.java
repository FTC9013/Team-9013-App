/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
 * This OpMode executes a POV Game style Teleop for a direct drive robot
 * The code is structured as a LinearOpMode
 *
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the arm using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */


public abstract class PrimaryOpMode2425 extends LinearOpMode
{
  
  /* Declare OpMode members. */
  public MastArm mast;
  public MecanumDriveChassis driveChassis;
  public DistanceSensors distanceSensors;
  public ArmControl arm;
  static final int HOOK_POSITION = 2450;
  //public Blang blang;
  
  public abstract void turnColor();
  
  @Override
  public void runOpMode()
  {
    mast = new MastArm(hardwareMap, telemetry);
    driveChassis = new MecanumDriveChassis(hardwareMap, telemetry);
    distanceSensors = new DistanceSensors(hardwareMap, telemetry);
    arm = new ArmControl(hardwareMap, telemetry);
    //blang = new Blang(hardwareMap);
    telemetry.addData(">", "Robot Ready. Press Play.");
    telemetry.addData(">", "9013 is the BeSt!>!>!!>!!!!!!111!!!.");
    telemetry.update();
    turnColor();
    
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
    //driveChassis.testWheels();
    //arm.reset();
    arm.reset();
    // run until the end of the match (driver presses STOP)
    //2 driver controls:
    while (opModeIsActive())
    {
      if (gamepad2.dpad_up)
      {
        mast.mastUp();
      } else if (gamepad2.dpad_down)
      {
        mast.mastDown();
      } else
      {
        mast.mastStop();
      }
      if (gamepad2.left_stick_y > 0.75)
      {
        arm.lower();
      } else if (gamepad2.left_stick_y < -0.75)
      {
        arm.raise();
        
      } else
      {
        arm.stop();
        
      }
      if (gamepad2.a)
      {
        arm.openGripper();
      }
      if (gamepad2.b)
      {
        arm.closeGripper();
      }
      if (gamepad2.y)
      {
        arm.extend();
      } else if (gamepad2.x)
      {
        telemetry.addLine("Retracting");
        arm.retract();
      } else
      {
        arm.stopExtending();
      }
      if (gamepad1.left_trigger > 0.75 && gamepad1.right_trigger > 0.75)
      {
        //hangSpecimen();
      }
      
      telemetry.addData("LStickY", gamepad1.left_stick_y * -1);
      telemetry.addData("LStickX", gamepad1.left_stick_x);
      telemetry.addData("vD: ", 1000);
      arm.printSensors();
      distanceSensors.printSensors();
      telemetry.update();
      if (gamepad1.left_bumper && gamepad1.right_bumper)
      {
        driveChassis.drive(-gamepad1.left_stick_y, -gamepad1.right_stick_x,
          -gamepad1.left_stick_x, false);
      } else
      {
        driveChassis.drive(gamepad1.left_stick_y, gamepad1.right_stick_x,
          gamepad1.left_stick_x, gamepad1.left_bumper);
      }
// Pace this loop so jaw action is reasonable speed.
      sleep(50);
    }
  }
  
  public void hangSpecimen()
  {
    arm.moveArmTo(HOOK_POSITION);
    if (triggersPressed())
      return;
    driveChassis.straighten(0);
    if (triggersPressed())
      return;
    stopBeforeBackWall(50);
    if (triggersPressed())
      return;
    arm.extend();
    if (triggersPressed())
      return;
    sleep(250);
    if (triggersPressed())
      return;
    arm.stopExtending();
    if (triggersPressed())
      return;
    arm.openGripper();
  }
  
  public void stopBeforeBackWall(double distBack)
  {
    double distTravel = distanceSensors.backDistance() - distBack;
    driveChassis.moveBackward(distTravel);
    telemetry.addData("Distance travel:", distTravel);
    telemetry.addData("back distance sensor", distanceSensors.backDistance());
    telemetry.update();
  }
  
  public boolean triggersPressed()
  {
    return (gamepad1.left_trigger > 0.75 && gamepad1.right_trigger > 0.75);
  }
}
  /*public void positionForArm()
  {
    driveChassis.moveBackward(prop_sensors.backDistance() - 1);
    arm.armRaise();
  }*/
