/*   MIT License
 *   Copyright (c) [2026] [Base 10 Assets, LLC]
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

/*
 * This file includes a teleop (driver-controlled) file for the goBILDA® StarterBot with Mecanum
 * Wheels for the 2026-2027 FIRST® Tech Challenge. On top of a mecanum wheel drivetrain, it uses
 * one motor driving an intake roller, two servos which pull elements out of corners, and a high-speed
 * launcher motor.
 *
 * Likely the most niche concept we'll use in this example is closed-loop motor velocity control.
 * This control method reads the current speed as reported by the motor's encoder and applies a varying
 * amount of power to reach, and then hold a target velocity. The FTC SDK calls this control method
 * "RUN_USING_ENCODER". This contrasts to the default "RUN_WITHOUT_ENCODER" where you control the power
 * applied to the motor directly.
 * Since the dynamics of a launcher wheel system varies greatly from those of most other FTC mechanisms,
 * we will also need to adjust the "PIDF" coefficients with some that are a better fit for our application.
 */

@TeleOp(name = "Basic Chassis TeleOp", group = "StarterBot")
//@Disabled
public class BasicChassisTeleOp extends OpMode
{
  
  // Declare OpMode members.
  private DcMotor leftFrontDrive = null;
  private DcMotor leftBackDrive = null;
  private DcMotor rightFrontDrive = null;
  private DcMotor rightBackDrive = null;
  
  
  /*
   * These four variables store the power we need to apply to the motors. In other cases, we may
   * choose to declare these variables inside the mecanumDrive() function, instead we declare them
   * here so that we can access them in our main loop for telemetry.
   */
  double leftFrontPower;
  double rightFrontPower;
  double leftBackPower;
  double rightBackPower;
  
  
  /*
   * Code to run ONCE when the driver hits INIT
   */
  @Override
  public void init()
  {
    
    /*
     * Initialize the hardware variables. Note that the strings used here as parameters
     * to 'get' must correspond to the names assigned during the robot configuration
     * step.
     */
    leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFront");
    rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
    leftBackDrive = hardwareMap.get(DcMotor.class, "leftRear");
    rightBackDrive = hardwareMap.get(DcMotor.class, "rightRear");
    
    /*
     * To drive forward, most robots need the motor on one side to be reversed,
     * because the axles point in opposite directions. Pushing the left stick forward
     * MUST make robot go forward. So adjust these two lines based on your first test drive.
     * Note: The settings here assume direct drive on left and right wheels. Gear
     * Reduction or 90 Deg drives may require direction flips
     */
    leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
    rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
    leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
    rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
    
    /*
     * Setting zeroPowerBehavior to BRAKE enables a "brake mode". This causes the motor to
     * slow down much faster when it is coasting. This creates a much more controllable
     * drivetrain. As the robot stops much quicker.
     */
    leftFrontDrive.setZeroPowerBehavior(BRAKE);
    rightFrontDrive.setZeroPowerBehavior(BRAKE);
    leftBackDrive.setZeroPowerBehavior(BRAKE);
    rightBackDrive.setZeroPowerBehavior(BRAKE);
    
    
    
    
    
    /*
     * Tell the driver that initialization is complete.
     */
    telemetry.addData("Status", "Initialized");
  }
  
  /*
   * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
   */
  @Override
  public void init_loop()
  {
  }
  
  /*
   * Code to run ONCE when the driver hits START
   */
  @Override
  public void start()
  {
  }
  
  /*
   * Code to run REPEATEDLY after the driver hits START but before they hit STOP
   */
  @Override
  public void loop()
  {
    /*
     * Here we call a function called mecanumDrive. The mecanumDrive function takes the input from
     * the joysticks, and applies power to the drive motors to move the robot as requested
     * by the driver. Moving the left joystick forwards/back moves all motors forwards/back,
     * moving the right joystick left/right rotates the robot clockwise or counterclockwise,
     * and moving the left joystick left moves the motors in the right way to create a sideways
     * "strafe" movement. Combinations of these inputs can be used to create more complex maneuvers.
     * Note, moving the joystick forward on most gamepads results in a negative signal, so
     * we invert it before passing it to the function.
     */
    mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
    
    
    telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftFrontPower, rightFrontPower);
    telemetry.addData("Triggers", "left (%.2f, right (%.2f)", gamepad1.left_trigger, gamepad1.right_trigger);
  }
  
  /*
   * Code to run ONCE after the driver hits STOP
   */
  @Override
  public void stop()
  {
  }
  
  void mecanumDrive(double forward, double strafe, double rotate)
  {
    leftFrontPower = forward + strafe + rotate;
    rightFrontPower = forward - strafe - rotate;
    leftBackPower = forward - strafe + rotate;
    rightBackPower = forward + strafe - rotate;
    
    double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
    max = Math.max(max, Math.abs(leftBackPower));
    max = Math.max(max, Math.abs(rightBackPower));
    
    if (max > 1.0)
    {
      leftFrontPower /= max;
      rightFrontPower /= max;
      leftBackPower /= max;
      rightBackPower /= max;
    }
    
    /*
     * Send calculated power to wheels
     */
    leftFrontDrive.setPower(leftFrontPower);
    rightFrontDrive.setPower(rightFrontPower);
    leftBackDrive.setPower(leftBackPower);
    rightBackDrive.setPower(rightBackPower);
  }
  
  
}
