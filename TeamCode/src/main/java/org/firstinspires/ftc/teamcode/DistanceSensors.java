/* Copyright (c) 2017-2020 FIRST. All rights reserved.
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

import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Arrays;

import androidx.annotation.NonNull;

/*
 * This OpMode shows how to use a color sensor in a generic
 * way, regardless of which particular make or model of color sensor is used. The OpMode
 * assumes that the color sensor is configured with a name of "sensor_color".
 *
 * There will be some variation in the values measured depending on the specific sensor you are using.
 *
 * You can increase the gain (a multiplier to make the sensor report higher values) by holding down
 * the A button on the gamepad, and decrease the gain by holding down the B button on the gamepad.
 *
 * If the color sensor has a light which is controllable from software, you can use the X button on
 * the gamepad to toggle the light on and off. The REV sensors don't support this, but instead have
 * a physical switch on them to turn the light on and off, beginning with REV Color Sensor V2.
 *
 * If the color sensor also supports short-range distance measurements (usually via an infrared
 * proximity sensor), the reported distance will be written to telemetry. As of September 2020,
 * the only color sensors that support this are the ones from REV Robotics. These infrared proximity
 * sensor measurements are only useful at very small distances, and are sensitive to ambient light
 * and surface reflectivity. You should use a different sensor if you need precise distance measurements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */


public class DistanceSensors
{
  
  public double frontDistance()
  {
    return frontSensor.getDistance(DistanceUnit.CM);

    
    /*double readings[] = new double[10];
    for (int i = 0; i <= 9; i++)
    {
      readings[i] = frontSensor.getDistance(DistanceUnit.CM);
      
    }
    Arrays.sort(readings);
    
    double averageReadings = 0;
    for (int i = 4; i <= 6; i++)
    {
      telemetry.addData("font dist is", readings[i]);
      averageReadings += readings[i];
    }
    
    return averageReadings / 3;
    */
  }
  
  public double leftDistance()
  {
    return leftSensor.getDistance(DistanceUnit.CM);
    /*double readings[] = new double[10];
    for (int i = 0; i <= 9; i++)
    {
      readings[i] = leftSensor.getDistance(DistanceUnit.CM);
      
    }
    Arrays.sort(readings);
    
    double averageReadings = 0;
    for (int i = 4; i <= 6; i++)
    {
      telemetry.addData("left dist is", readings[i]);
      averageReadings += readings[i];
    }
    
    return averageReadings / 3;
    */
    
  }
  
  public double rightDistance()
  {
    //return rightSensor.getDistance(DistanceUnit.CM);
    
    double readings[] = new double[10];
    for (int i = 0; i <= 9; i++)
    {
      readings[i] = rightSensor.getDistance(DistanceUnit.CM);
      
    }
    Arrays.sort(readings);
    
    double averageReadings = 0;
    for (int i = 4; i <= 6; i++)
    {
      telemetry.addData("rigt dist is", readings[i]);
      averageReadings += readings[i];
    }
    
    return averageReadings / 3;
    
  }
  
  public double backDistance()
  {
    return backSensor.getDistance(DistanceUnit.CM);
    /*double readings[] = new double[10];
    for (int i = 0; i <= 9; i++)
    {
      readings[i] = backSensor.getDistance(DistanceUnit.CM);
      
    }
    Arrays.sort(readings);
    
    double averageReadings = 0;
    for (int i = 4; i <= 6; i++)
    {
      telemetry.addData("back dist is", readings[i]);
      averageReadings += readings[i];
    }
    
    return averageReadings / 3;
    
     */
    
  }
  
  /** The colorSensor field will contain a reference to our color sensor hardware object */
  DistanceSensor frontSensor;
  DistanceSensor rightSensor;
  DistanceSensor leftSensor;
  DistanceSensor backSensor;
  double distanceThingy = 8;
  
  /** The relativeLayout field is used to aid in providing interesting visual feedback
   * in this sample application; you probably *don't* need this when you use a color sensor on your
   * robot. Note that you won't see anything change on the Driver Station, only on the Robot Controller. */
  View relativeLayout;
  private final Telemetry telemetry;
  
  enum PropSide
  {Right, Left, Front, No}
  
  DistanceSensors(@NonNull HardwareMap hardwareMap, Telemetry theTelemetry)
  {
    telemetry = theTelemetry;
    int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
    relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
    frontSensor = hardwareMap.get(DistanceSensor.class, "frontSensor");
    leftSensor = hardwareMap.get(DistanceSensor.class, "leftSensor");
    rightSensor = hardwareMap.get(DistanceSensor.class, "rightSensor");
    backSensor = hardwareMap.get(DistanceSensor.class, "backSensor");
  }
  
  
  
  /*
   * The runOpMode() method is the root of this OpMode, as it is in all LinearOpModes.
   * Our implementation here, though is a bit unusual: we've decided to put all the actual work
   * in the runSample() method rather than directly in runOpMode() itself. The reason we do that is
   * that in this sample we're changing the background color of the robot controller screen as the
   * OpMode runs, and we want to be able to *guarantee* that we restore it to something reasonable
   * and palatable when the OpMode ends. The simplest way to do that is to use a try...finally
   * block around the main, core logic, and an easy way to make that all clear was to separate
   * the former from the latter in separate methods.
   */
  
  
  protected PropSide detectProp()
  {
    
    
    telemetry.addData("Port 0, Front Sensor | Distance (cm)", "%.3f", ((DistanceSensor) frontSensor).getDistance(DistanceUnit.CM));
    if (frontSensor.getDistance(DistanceUnit.CM) < distanceThingy)
    {
      telemetry.addLine("It on de front");
      return PropSide.Front;
    }
    
    
    telemetry.addData("Port 1, Left Sensor | Distance (cm)", "%.3f", ((DistanceSensor) leftSensor).getDistance(DistanceUnit.CM));
    if (leftSensor.getDistance(DistanceUnit.CM) < distanceThingy)
    {
      telemetry.addLine("It on de lefte");
      return PropSide.Left;
    }
    
    
    telemetry.addData("Port 2, Right Sensor | Distance (cm)", "%.3f", ((DistanceSensor) rightSensor).getDistance(DistanceUnit.CM));
    if (rightSensor.getDistance(DistanceUnit.CM) < distanceThingy)
    {
      telemetry.addLine("It on de rite");
      return PropSide.Right;
    }
    
    return PropSide.No;
  }
  
  public void printSensors()
  {
    telemetry.addData("Front Sensor | Distance (cm)", "%.3f", frontSensor.getDistance(DistanceUnit.CM));
    telemetry.addData("Left Sensor | Distance (cm)", "%.3f", leftSensor.getDistance(DistanceUnit.CM));
    telemetry.addData("Right Sensor | Distance (cm)", "%.3f", rightSensor.getDistance(DistanceUnit.CM));
    telemetry.addData("Back Sensor | Distance (cm)", "%.3f", backSensor.getDistance(DistanceUnit.CM));
  }
}