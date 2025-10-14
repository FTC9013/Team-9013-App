package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class DacodAuto extends LinearOpMode
{
  //Need to add in movement code
  public AprilTagCamera aprilTagCamera;
  public ConveyorBelt conveyorBelt;
  public Launcher launcher;
  
  public ConceptVisionColorSensor conceptVisionColorSensor;
  
  public void runOpMode()
  {
    aprilTagCamera = new AprilTagCamera(this);
    conceptVisionColorSensor = new ConceptVisionColorSensor(hardwareMap, telemetry);
    conveyorBelt = new ConveyorBelt(hardwareMap, telemetry);
    launcher = new Launcher(hardwareMap, telemetry);
    telemetry.addLine("Initialized");
    telemetry.update();
    waitForStart();
    Motif motifPattern = aprilTagCamera.detectAprilTag();
    
    
    //the auto method
//    findObelisk();
//    aprilTagCamera.detectAprilTag();//we need to improve on this line later
    launchArtifacts();
    collectArtifacts(aprilTagCamera.detectAprilTag());
    launchArtifacts();
    collectArtifacts(aprilTagCamera.detectAprilTag());
    launchArtifacts();
    
    
  }
  
  public void findObelisk()
  {
    //a bunch of movement code to get to the obelisk
    //also pointing at the obelisk
    
  }
  
  public void goToLaunchPosition()
  {
    //go to the launch position with a bunch of movement code
    //spikeNumb is which spike we getting
    
  }
  
  public void collectArtifacts(Motif desiredSpike)
  {
    //go to the spike marks and collect artifacts
    if (desiredSpike == Motif.GPP)
    {
    
    } else if (desiredSpike == Motif.PPG)
    {
    
    } else if (desiredSpike == Motif.PGP)
    {
    
    }
    
    
  }
  
  /*
   Auto routine:
   call findObelisk()
   do something with the aprilTagCamera.detectAprilTag() method
   call goToLaunchPosition()
   call launcher.startLaunching()
   call collectArtifacts()
   call goToLaunchPosition()
   call launcher.startLaunching()
   repeat up 3 steps above up to 3 times
  */
  public void launchArtifacts()
  {
    goToLaunchPosition();
    launcher.startLaunching();
    launcher.stopLaunching();
  }
}

