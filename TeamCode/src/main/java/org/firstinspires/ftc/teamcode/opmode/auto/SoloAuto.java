package org.firstinspires.ftc.teamcode.opmode.auto;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.commands.TransferCommand;
import org.firstinspires.ftc.teamcode.commands.TurretDynamicVelocityCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.DeployableRamp;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

@Autonomous (name = "SoloAuto")
public class SoloAuto extends OpMode {
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Path startGardenFast;
    Path gardenSlow;
    Path preUnderHive;
    Path shootAfterHive;
    Path pathFlower1;
    Path slowFlower1;
    Path shootFirstFlower;
    Path pathFlower2;
    Path slowFlower2;
    Path shootFlower2;
    Path shootToPark;

    RobotBase robotBase;
    ZoneControl zoneControl;
    PolygonZone robotZone = new PolygonZone(18, 18);

    Pose start = poseFactory.of(51,5,180);
    Pose gardenBrake = poseFactory.of(20,5,180);
    Pose garden = poseFactory.of(7,5,180);
    Pose lineUpToHive = poseFactory.of(58,41,90);
    Pose shootFirstTime = poseFactory.of(52,127,90);
    Pose goUpToFirstFlower = poseFactory.of(47,117,90);
    Pose firstFlower = poseFactory.of(47,127,90);
    Pose shootFirstFlow= poseFactory.of(62,116,90);
    Pose goToNextFlower = poseFactory.of(18, 49, 180);
    Pose secondFlower = poseFactory.of(18,52,180);
    Pose shootSecondTime = poseFactory.of(53,24,90);
 //   Pose fixPark = poseFactory.of(30,33,90);
    Pose park = poseFactory.of(14,98,90);
    Pose parkControl = poseFactory.of(9, 47, 90);

    SequentialCommandGroup commandGroup;
    @Override
    public void init(){
        CommandScheduler.getInstance().reset();

        follower = Constants.create(hardwareMap);
        startGardenFast = line(start,gardenBrake).linear(start,gardenBrake).with(Constants.foresightConfig.maxPathSpeed.at(0.8));
        gardenSlow = line(gardenBrake, garden).linear(gardenBrake,garden).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
        preUnderHive = line(garden,lineUpToHive ).linear(garden,lineUpToHive).with(Constants.foresightConfig.maxPathSpeed.at(1.0));
        shootAfterHive = line(lineUpToHive,shootFirstTime).linear(lineUpToHive,shootFirstTime);
        pathFlower1 = line(shootFirstTime, goUpToFirstFlower).linear(shootFirstTime,goUpToFirstFlower);
        slowFlower1 = line(goUpToFirstFlower,firstFlower).linear(goUpToFirstFlower,firstFlower).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
        shootFirstFlower = line(firstFlower,shootFirstFlow).constant(firstFlower).with(Constants.foresightConfig.maxPathSpeed.at(1.0));
        pathFlower2 = line(firstFlower, goToNextFlower).linear(firstFlower, goToNextFlower);
        slowFlower2 = line(goToNextFlower, secondFlower).linear(goToNextFlower,secondFlower).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
        shootFlower2 = line(secondFlower, shootSecondTime).linear(secondFlower,shootSecondTime).with(Constants.foresightConfig.maxPathSpeed.at(1.0));
        shootToPark = line(shootSecondTime,park).linear(shootSecondTime,park);

        follower.setPose(start);

        robotBase = new RobotBase(hardwareMap);
        zoneControl = new ZoneControl(robotZone, follower);
        robotBase.tranferBlockersubsystem.setPosition(TransferBlocker.TransferBlockerPosition.STOP);
        robotBase.deployableRampSubsystem.setPosition(DeployableRamp.DeployableRampPosition.AWAY);

        commandGroup = new SequentialCommandGroup(
               //new WaitUntilCommand(() -> robotBase.launcherSubsystem.isAtSpeed()).withTimeout(1500),
                new WaitCommand(4000),
                new TransferCommand(robotBase),
                new WaitCommand(1000),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(-1)),
                new FollowPathCommand(follower,startGardenFast),
                new FollowPathCommand(follower,gardenSlow),
                new FollowPathCommand(follower, preUnderHive),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0)),
                new FollowPathCommand(follower,shootAfterHive),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(-1)),
                new WaitCommand(1000),
                new TransferCommand(robotBase),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(-1)),
                new InstantCommand(() -> robotBase.deployableRampSubsystem.deploy()),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower, pathFlower1),
                new InstantCommand(() -> robotBase.deployableRampSubsystem.deploy()),
                new FollowPathCommand(follower,slowFlower1),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(-1)),
                new WaitCommand(1000),
                new FollowPathCommand(follower, shootFirstFlower),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(.5)),
                new FollowPathCommand(follower, pathFlower2),
                new FollowPathCommand(follower, slowFlower2),
                new WaitCommand(1000),
                new FollowPathCommand( follower, shootFlower2),
                new InstantCommand(() -> robotBase.deployableRampSubsystem.undeploy()),
                //new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new WaitCommand(1000),
                new TransferCommand(robotBase),
                //new LaunchCommand(robotBase, 800),
                //new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),*/
                new WaitCommand(2000),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0)),
               // new TransferCommand(robotBase),
                new FollowPathCommand(follower, shootToPark));




    }

    @Override
    public void start(){
        CommandScheduler.getInstance().schedule(
                commandGroup
        );
        CommandScheduler.getInstance().schedule(new TurretDynamicVelocityCommand(zoneControl, robotBase, follower));
    }

    @Override
    public void loop(){
        follower.update();
        zoneControl.updateRobotZone();
        CommandScheduler.getInstance().run();
        telemetry.addData("X: ", follower.pose().x());
        telemetry.addData("Y: ", follower.pose().y());
        telemetry.addData("Heading: ", follower.pose().heading());
        telemetry.update();
    }

    @Override
    public void stop() {
        DataStorage.position = follower.pose();
    }
}