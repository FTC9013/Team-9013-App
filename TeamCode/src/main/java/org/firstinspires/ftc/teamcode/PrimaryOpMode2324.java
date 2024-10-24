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
import com.qualcomm.robotcore.hardware.DcMotor;

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


public abstract class PrimaryOpMode2324 extends LinearOpMode
{
  
  /* Declare OpMode members. */
  public DcMotor driveMotor = null;
  /*public MastArm mast;
  public AirplaneLauncher airplane;*/
  public MecanumDriveChassis driveChassis;
  public Prop_Sensors prop_sensors;
  //public PixelDropper purplePixelDropper;
  //public PixelDropper yellowPixelDropper;
  //public ArmControl arm;
  //public Blang blang;
  
  public abstract void turnColor();
  
  @Override
  public void runOpMode()
  {
    //mast = new MastArm(hardwareMap, telemetry);
    //airplane = new AirplaneLauncher(hardwareMap, telemetry);
    driveChassis = new MecanumDriveChassis(hardwareMap, telemetry);
    prop_sensors = new Prop_Sensors(hardwareMap, telemetry);
    //purplePixelDropper = new PixelDropper(hardwareMap, telemetry, "pixelDropperPurple");
    //yellowPixelDropper = new PixelDropper(hardwareMap, telemetry, "pixelDropperYellow");
    //arm = new ArmControl(hardwareMap, telemetry);
    //blang = new Blang(hardwareMap);
    telemetry.addData(">", "Robot Ready. Press Play.");
    telemetry.addData(">", "Remember:stay skibidi. Devin is NOT a mewer.");
    telemetry.update();
    turnColor();
    
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
    //driveChassis.testWheels();
    //arm.armLower(); //This actually raises it
    //sleep(650);
    //arm.armStop();
    
    // run until the end of the match (driver presses STOP)
    //2 driver controls:
    while (opModeIsActive())
    {
      /*  gamepad1.right_trigger > 0.75 && gamepad1.left_trigger > 0.75)
      {
        airplane.launch();
        sleep(2000);
        airplane.resetLauncher();
      }
      if (gamepad2.right_trigger > 0.75 && gamepad2.x)
      {
        airplane.resetLauncher();
      }*/
      /*if (gamepad2.right_bumper && gamepad2.right_trigger > 0.75)
      {
        purplePixelDropper.drop_pixel();
      }
      if (gamepad2.left_bumper && gamepad2.left_trigger > 0.75)
      {
        yellowPixelDropper.drop_pixel();
      }*/
      /*if (gamepad2.dpad_up)
      {
        mast.mastUp();
      } else if (gamepad2.dpad_down)
      {
        mast.mastDown();
      } else
      {
        mast.mastStop();
      }*/
      /*if (gamepad2.left_stick_y > 0.75)
      {
        arm.armRaise();
      } else if (gamepad2.left_stick_y < -0.75)
      {
        arm.armLower();
      } else
      {
        arm.armStop();
      }
      if (gamepad2.a)
      {
        arm.toggleGripper();
        sleep(400);
      }*/
      /*if (gamepad2.left_bumper)
      {
        purplePixelDropper.drop_Purple();
      }
      if (gamepad2.right_bumper)
      {
        purplePixelDropper.lift();
      }*/
      
      telemetry.update();
      telemetry.addData("LStickY", gamepad1.left_stick_y * -1);
      telemetry.addData("LStickX", gamepad1.left_stick_x);
      telemetry.addData("vD: ", 1000);
      telemetry.update();
      if (gamepad1.left_bumper && gamepad1.right_bumper)
      {
        driveChassis.drive(-gamepad1.left_stick_y, -gamepad1.left_stick_x,
          -gamepad1.right_stick_x, false);
      } else
      {
        driveChassis.drive(gamepad1.left_stick_y, gamepad1.left_stick_x,
          gamepad1.right_stick_x, gamepad1.left_bumper);
      }
// Pace this loop so jaw action is reasonable speed.
      sleep(50);
    }
  }
  
  /*public void positionForArm()
  {
    driveChassis.moveBackward(prop_sensors.backDistance() - 1);
    arm.armRaise();
  }*/
}