package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


// ticks per centepeder = 17.7914
public abstract class OdonomentryIntoTheDebt extends LinearOpMode
{
  public Blang blang;
  public SimplifiedOdometryRobotInches driveChassisOdom;
  public DistanceSensors distanceSensors;
  public ArmControl arm;
  public DistanceSensors propSensors;
  static final double DEFAULT_DRIVE_POWER = 1;
  static final double DEFAULT_TURN_POWER = 0.8;
  static final double DEFAULT_STRAFE_POWER = 0.8;
  static final double DEFAULT_HOLD_TIME = 0;
  static final double DEFAULT_TIMEOUT = 2;
  double tickPerCm = 20.24278;
  double maxSpeed = 0.6;
  double minSpeed = 0.1;
  double decelDistCm = 20;
  static final int RAISE_ARM = 3000;
  static final int HOOK_POSITION = 2800;
  static final int STARTING_POSITION = 2500;
  static final int DROP_POSITION = 3350;
  static final int MAX_EXTENSION = 2400;
  static final int MEDIUM_ARM = 2000;
  static final int INITIAL_EXTENSION = 2700;
  
  
  // a timer for the various automation activities.
  @Override
  public void runOpMode()
  {
    arm = new ArmControl(this);
    blang = new Blang(hardwareMap);
    distanceSensors = new DistanceSensors(hardwareMap, telemetry);
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    // setup a instance of our drive system
    // Declare OpMode members.
    driveChassisOdom = new SimplifiedOdometryRobotInches(this);
    driveChassisOdom.initialize(false);
    turnColor();
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
    driveChassisOdom.resetHeading();
    runAuto();
    //goFromLeftWall(30);
    //sleep(5000);
  }
  
  public abstract void runAuto();
  
  public abstract void turnColor();
  
  public void goAwayFromLeftWall(double distRight)
  {
    telemetry.addData("Left sensor:", distanceSensors.leftDistance());
    telemetry.update();
    double distTravel = distanceSensors.leftDistance() - distRight;
    strafe(distTravel);
    telemetry.addData("Left sensor:", distanceSensors.leftDistance());
    telemetry.update();
  }
  
  public void goAwayFromRightWall(double distLeft)
  {
    double distTravel = distanceSensors.rightDistance() - distLeft;
    strafe(-distTravel);
  }
  
  public void stopBeforeBackWall(double distBack)
  {
    double distTravel = distanceSensors.backDistance() - distBack;
    drive(-distTravel);
    telemetry.addData("Distance travel:", distTravel);
    telemetry.addData("back distance sensor", distanceSensors.backDistance());
    telemetry.update();
  }
  
  public void initialize()
  {
    arm.startExtendingTo(INITIAL_EXTENSION, 1);
    arm.resetAuto();
    sleep(100);
    arm.retractAuto();
    arm.startMovingTo(-1650);
  }
  
  public void hookSample()
  {
    //arm.moveArmTo(HOOK_POSITION);
    drive(70);
    telemetry.addLine("sigma sigma boy");
    //arm.extendForTime(0.4);
    arm.openGripper();
    sleep(500);
    //sleep(500);
    drive(-45.5);
  }
  
  
  public void strafeToBasket()
  {
    telemetry.addLine("Strafing left");
    telemetry.update();
    arm.startMovingTo(-3800);
    strafe(93, 5);
  }
  
  public void grabAndDropSample()
  {
    arm.closeGripper();
    arm.reset();
    sleep(200);
    arm.startMovingTo(4300);
    arm.startExtendingTo(1850);
    telemetry.addLine("Turning");
    telemetry.update();
    turn(90, 2);
    telemetry.addLine("Strafing");
    telemetry.update();
    goAwayFromLeftWall(15);
    drive(52);
    arm.openGripper();
    arm.moveArmTo(3600);
    sleep(300);
    arm.moveArmTo(4000);
    arm.retractAuto();
    telemetry.addLine("sample is drop");
    telemetry.update();
    //ching billing
  }
  
  //in massive debt. time for tax evasion.
  public void secondSample()
  {
    telemetry.addLine("strafing");
    telemetry.update();
    strafe(-24, 1);
    telemetry.addLine("driving");
    telemetry.update();
    drive(-29, 1);
    telemetry.addLine("turning");
    telemetry.update();
    turn(0, 3.25);
    arm.moveArmTo(0);
    arm.closeGripper();
    sleep(1500);
    arm.moveArmTo(4100);
    arm.startExtendingTo(1850);
    turn(90, 2);
    drive(25, 1);
    strafe(26, 1);
    arm.openGripper();
    arm.moveArmTo(3600);
    sleep(500);
    //arm.moveArmTo(4300);
    telemetry.addLine("sample is drop");
    telemetry.update();
    
  }
  
  public void afterSecond()
  
  {
    arm.moveArmTo(3800);
    drive(-30);
    arm.retractAuto();
    arm.startMovingTo(1780);
    turn(-45, 2);
    drive(75.25);
    turn(-50);
  }
  
  public void tripleSample()
  {
    drive(24.5);
    strafe(48);
    arm.startExtendingTo(1850);
    turn(90, 2);
    goAwayFromLeftWall(15);
    drive(52);
    arm.openGripper();
    arm.moveArmTo(3600);
    sleep(300);
    arm.moveArmTo(4000);
    arm.retractAuto();
    telemetry.addLine("sample is drop");
    telemetry.update();
    strafe(-53);
    drive(-24.5);
    turn(0, 2);
    grabAndDropSample();
    secondSample();
  }
  
  public void touchBar()
  {
    
    drive(-60);
    arm.retractAuto();
    turn(0);
    
    arm.startMovingTo(STARTING_POSITION - 450);
    drive(95);
    turn(-90);
    
    drive(distanceSensors.frontDistance() - 1);
    
    //6C 69 67 6D 61 SPECIAL VALUES HEX TRANSLATION
  }
  
  
  public void drive(double distanceCm)
  {
    driveChassisOdom.drive(distanceCm, DEFAULT_DRIVE_POWER, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
  }
  
  public void drive(double distanceCm, double timeout)
  {
    driveChassisOdom.drive(distanceCm, DEFAULT_DRIVE_POWER, DEFAULT_HOLD_TIME, timeout);
  }
  
  public void strafe(double distanceCm)
  {
    driveChassisOdom.strafe(distanceCm, DEFAULT_STRAFE_POWER, DEFAULT_HOLD_TIME, DEFAULT_TIMEOUT);
  }
  
  public void strafe(double distanceCm, double timeout)
  {
    driveChassisOdom.strafe(distanceCm, DEFAULT_STRAFE_POWER, DEFAULT_HOLD_TIME, timeout);
  }
  
  public void turn(double degree)
  {
    driveChassisOdom.turnTo(degree, DEFAULT_DRIVE_POWER, DEFAULT_HOLD_TIME, 3);
  }
  
  public void turn(double degree, double timeout)
  {
    driveChassisOdom.turnTo(degree, DEFAULT_TURN_POWER, DEFAULT_HOLD_TIME, timeout);
  }
}

/* we the sigmas:
3.141592653589793238462643383279502884197169399375105820974944592307816406286208998
28034825342117067982148086513282306647093844609550582231725359408128481117450284102
70193852110555964462294895493038196442881097566593344612847564823378678316527120190
91456485669234603486104543266482133936072602491412737245870066063155881748815209209
6282925409171536436789259036001133053054882046652138414695194151160943305727036575
9195309218611738193261179310511854807446237996274956735188575272489122793818301194
9129833673362440656643086021394946395224737190702179860943702770539217176293176752
38467481846766940513200056812714526356082778577134275778960917363717872146844090122
4953430146549585371050792279689258923542019956112129021960864034418159813629774771
3099605187072113499999983729780499510597317328160963185950244594553469083026425223
0825334468503526193118817101000313783875288658753320838142061717766914730359825349
042875546873115956286388235378759375195778185778053217122680661300192787661119590
921642019893809525720106548586327886593615338182796823030195203530185296899577362
259941389124972177528347913151557485724245415069595082953311686172785588907509838
175463746493931925506040092770167113900984882401285836160356370766010471018194295
55961989467678374494482553797747268471040475346462080466842590694912933136770289
8915210475216205696602405803815019351125338243003558764024749647326391419927260
42699227967823547816360093417216412199245863150302861829745557067498385054945885
869269956909272107975093029553211653449872027559602364806654991198818347977535
6636980742654252786255181841757467289097777279380008164706001614524919217321721
477235014144197356854816136115735255213347574184946843852332390739414333454776
241686251898356948556209921922218427255025425688767179049460165346680498862723
279178608578438382796797668145410095388378636095068006422512520511739298489608
41284886269456042419652850222106611863067442786220391949450471237137869609563
6437191728746776465757396241389086583264599581339047802759009946576407895126
9468398352595709825822620522489407726719478268482601476990902640136394437455
305068203496252451749399651431429809190659250937221696461515709858387410597
88595977297549893016175392846813826868386894277415599185592524595395943104
99725246808459872736446958486538367362226260991246080512438843904512441365
4976278079771569143599770012961608944169486855584840635342207222582848864
81584560285060168427394522674676788952521385225499546667278239864565961163
5488623057745649803559363456817432411251507606947945109659609402522887971
08931456691368672287489405601015033086179286809208747609178249385890097149
0967598526136554978189312978482168299894872265880485756401427047755513237964
1451523746234364542858444795265867821051141354735739523113427166102135969
5362314429524849371871101457654035902799344037420073105785390621983874478
08478489683321445713868751943506430218453191048481005370614680674919278
19119793995206141966342875444064374512371819217999839101591956181467514
26912397489409071864942319615679452080951465502252316038819301420937621
378559566389377870830390697920773467221825625996615014215030680384477345
49202605414665925201497442850732518666002132434088190710486331734649651
453905796268561005508106658796998163574736384052571459102897064140110971
206280439039759515677157700420337869936007230558763176359421873125147120532928191826186125867321579198414848829164470609575270695722091756711672291098169091528017350671274858322287183520935396572512108357915136988209144421006751033467110314126711136990865851639831501970165151168517143765761835155650
 */



