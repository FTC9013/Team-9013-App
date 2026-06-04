package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp
public class ExampleAprilTagUsage extends OpMode
{
  private Follower follower;
  private boolean following = false;
  private final Pose TARGET_LOCATION = new Pose(36, 36, 0); //Put the target location here
  private Position cameraPosition = new Position(DistanceUnit.INCH,
    -1, 6.5, 3.75, 0);
  private YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
    0, -60, 0, 0);
  private AprilTagProcessor aprilTag;
  
  /**
   * The variable to store our instance of the vision portal.
   */
  private VisionPortal visionPortal;
  
  private void initAprilTag()
  {
    
    
    // Create the AprilTag processor.
    aprilTag = new AprilTagProcessor.Builder()
      
      // The following default settings are available to un-comment and edit as needed.
      //.setDrawAxes(false)
      //.setDrawCubeProjection(false)
      //.setDrawTagOutline(true)
      //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
      //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
      //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
      .setCameraPose(cameraPosition, cameraOrientation)
      
      // == CAMERA CALIBRATION ==
      // If you do not manually specify calibration parameters, the SDK will attempt
      // to load a predefined calibration for your camera.
      //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
      // ... these parameters are fx, fy, cx, cy.
      
      .build();
    
    // Adjust Image Decimation to trade-off detection-range for detection-rate.
    // eg: Some typical detection data using a Logitech C920 WebCam
    // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
    // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
    // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second (default)
    // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second (default)
    // Note: Decimation can be changed on-the-fly to adapt during a match.
    //aprilTag.setDecimation(3);
    
    // Create the vision portal by using a builder.
    VisionPortal.Builder builder = new VisionPortal.Builder();
    
    // Set the camera (webcam vs. built-in RC phone camera).
    
    builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
    
    
    // Choose a camera resolution. Not all cameras support all resolutions.
    //builder.setCameraResolution(new Size(640, 480));
    
    // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
    //builder.enableLiveView(true);
    
    // Set the stream format; MJPEG uses less bandwidth than default YUY2.
    //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
    
    // Choose whether or not LiveView stops if no processors are enabled.
    // If set "true", monitor shows solid orange screen if no processors enabled.
    // If set "false", monitor shows camera view without annotations.
    //builder.setAutoStopLiveView(false);
    
    // Set and enable the processor.
    builder.addProcessor(aprilTag);
    
    // Build the Vision Portal, using the above settings.
    visionPortal = builder.build();
    
    // Disable or re-enable the aprilTag processor at any time.
    //visionPortal.setProcessorEnabled(aprilTag, true);
    
  }
  
  @Override
  public void init()
  {
    initAprilTag();
    
    follower = Constants.createFollower(hardwareMap);
    follower.setStartingPose(new Pose()); //set your starting pose
    
  }
  
  
  @Override
  public void loop()
  {
    follower.update();
    
    //if you're not using limelight you can follow the same steps: build an offset pose, put your heading offset, and generate a path etc
    
    if (!following)
    {
      follower.followPath(
        follower.pathBuilder()
          .addPath(new BezierLine(follower.getPose(), TARGET_LOCATION))
          .setLinearHeadingInterpolation(follower.getHeading(), TARGET_LOCATION.minus(follower.getPose()).getAsVector().getTheta())
          .build()
      );
    }
    Pose pose = getRobotPoseFromCamera();
    if (gamepad1.dpad_down)
    {
      visionPortal.stopStreaming();
    } else if (gamepad1.dpad_up)
    {
      visionPortal.resumeStreaming();
    }
    //This uses the aprilTag to relocalize your robot
    //You can also create a custom AprilTag fusion Localizer for the follower if you want to use this by default for all your autos
    if (pose != null)
    {
      follower.setPose(pose);
    }
    if (following && !follower.isBusy()) following = false;
    
  }
  
  
  private Pose getRobotPoseFromCamera()
  {
    
    List<AprilTagDetection> currentDetections = aprilTag.getDetections();
    telemetry.addData("# AprilTags Detected", currentDetections.size());
    double totalPoseX = 0;
    double totalPoseY = 0;
    double totalPoseHeading = 0;
    int counter = 0;
    // Step through the list of detections and display info for each one.
    for (AprilTagDetection detection : currentDetections)
    {
      if (detection.metadata != null)
      {
        telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
        // Only use tags that don't have Obelisk in them
        if (!detection.metadata.name.contains("Obelisk"))
        {
          totalPoseX += detection.robotPose.getPosition().x;
          totalPoseY += detection.robotPose.getPosition().y;
          //Adds the 180 in order to turn the range of yaw from [-180,180] to [0,360], avoiding further issues with averaging
          totalPoseHeading += detection.robotPose.getOrientation().getYaw(AngleUnit.DEGREES) + 180;
          telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)",
            detection.robotPose.getPosition().x,
            detection.robotPose.getPosition().y,
            detection.robotPose.getPosition().z));
          telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)",
            detection.robotPose.getOrientation().getPitch(AngleUnit.DEGREES),
            detection.robotPose.getOrientation().getRoll(AngleUnit.DEGREES),
            detection.robotPose.getOrientation().getYaw(AngleUnit.DEGREES)));
          counter += 1;
        }
      } else
      {
        telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
        telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
      }
    }   // end for() loop
    if (counter != 0)
    {
      double poseX = totalPoseX / counter;
      double poseY = totalPoseY / counter;
      //undoes the normalization to get back to [-180,180](see above)
      double poseHeading = (totalPoseHeading / counter) - 180;
      telemetry.addData("PoseX: ", poseX);
      telemetry.addData("PoseY: ", poseY);
      telemetry.addData("PoseZ: ", poseHeading);
      telemetry.update();
      return new Pose(poseX, poseY, poseHeading, FTCCoordinates.INSTANCE).getAsCoordinateSystem(PedroCoordinates.INSTANCE);
      
    } else
    {
      return null;
    }
    
    // Add "key" information to telemetry
    
  }   // end method telemetryAprilTag()
  
}