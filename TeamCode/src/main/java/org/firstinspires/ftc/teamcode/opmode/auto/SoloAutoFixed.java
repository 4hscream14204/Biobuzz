package org.firstinspires.ftc.teamcode.Opmode.auto;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.commands.TransferBlockerCommand;
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
import org.screamrobotics.SuperSCREAMLib.command.WaitUntilCommand;

import java.util.List;

@Autonomous (name = "SoloAuto Upload Please")
public class SoloAutoFixed extends OpMode {
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Path path1;
    Path path2;
    Path garden2;
    Path path3;
    Path pathFlower1;
    Path path4;
    Path pathFlower2;
    Path path5;
    Path jostleFlowerPath;
    Path path6;
    Path path7;

    RobotBase robotBase;
    ZoneControl zoneControl;
    PolygonZone robotZone = new PolygonZone(18, 18);

    Pose start = poseFactory.of(51,5,180);
    Pose gardenBrake = poseFactory.of(20,6.5,180);
    Pose garden = poseFactory.of(8,6.5,180);
    Pose lineUpToHive = poseFactory.of(62,41,90);
    Pose shootFirstTime = poseFactory.of(62,128,90);
    Pose goUpToFirstFlower = poseFactory.of(48,118,90);
    Pose firstFlower = poseFactory.of(47,127,90);
    Pose jostleFlower = poseFactory.of(49, 127, 90);
    Pose goToNextFlower = poseFactory.of(18.5, 49.5, 180);
    Pose secondFlower = poseFactory.of(16,50.5,180);
    Pose shootSecondTime = poseFactory.of(53,24,90);
    Pose park = poseFactory.of(9,120,0);
    Pose parkControl = poseFactory.of(9, 47, 90);
    SequentialCommandGroup commandGroup;
    List<LynxModule> allHubs;
    @Override
    public void init(){
        CommandScheduler.getInstance().reset();

        allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub: allHubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        follower = Constants.create(hardwareMap);
        path1 = line(start,gardenBrake).linear(start,gardenBrake).with(Constants.foresightConfig.maxPathSpeed.at(0.8));
        garden2 = line(gardenBrake, garden).linear(gardenBrake,garden).with(Constants.foresightConfig.maxPathSpeed.at(0.4));
        path2 = line(garden,lineUpToHive).linear(garden,lineUpToHive).with(Constants.foresightConfig.maxPathSpeed.at(1.0));
        path3 = line(lineUpToHive,shootFirstTime).linear(lineUpToHive,shootFirstTime);
        pathFlower1 = line(shootFirstTime, goUpToFirstFlower).linear(shootFirstTime,goUpToFirstFlower);
        path4 = line(goUpToFirstFlower,firstFlower).linear(goUpToFirstFlower,firstFlower).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
        jostleFlowerPath = line(firstFlower, jostleFlower).constant(jostleFlower).with(Constants.foresightConfig.maxPathSpeed.at(0.5));
//        pathFlower2 = line(firstFlower, goToNextFlower).linear(firstFlower, goToNextFlower).with(Constants.foresightConfig.maxPathSpeed.at(1.0));
//        path5 = line(goToNextFlower, secondFlower).linear(goToNextFlower,secondFlower).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
//        path6 = line(secondFlower, shootSecondTime).linear(secondFlower,shootSecondTime).with(Constants.foresightConfig.maxPathSpeed.at(1.0));
        path7 = line(jostleFlower, park).linear(shootSecondTime,park);

        follower.setPose(start);

        robotBase = new RobotBase(hardwareMap);
        zoneControl = new ZoneControl(robotZone, follower);
        robotBase.tranferBlockersubsystem.setPosition(TransferBlocker.TransferBlockerPosition.STOP);
        robotBase.deployableRampSubsystem.setPosition(DeployableRamp.DeployableRampPosition.AWAY);

        commandGroup = new SequentialCommandGroup(
                //new WaitUntilCommand(() -> robotBase.launcherSubsystem.isAtSpeed()).withTimeout(1500),
                new WaitCommand(2000),
                new TransferCommand(robotBase),
                //new LaunchCommand(robotBase, 800),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(-1)),
                new FollowPathCommand(follower,path1),
                new FollowPathCommand(follower,garden2),
                new FollowPathCommand(follower, path2),
                new FollowPathCommand(follower,path3),
                new WaitCommand(2000),
                //  new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new TransferCommand(robotBase),
                //new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower, pathFlower1),
                new InstantCommand(()->robotBase.tranferBlockersubsystem.setPosition(TransferBlocker.TransferBlockerPosition.RELEASE)),
                new InstantCommand(() -> robotBase.deployableRampSubsystem.deploy()),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(-0.4)),
                new FollowPathCommand(follower,path4),
                //new FollowPathCommand(follower, jostleFlowerPath),
                new WaitCommand(2000),
                new FollowPathCommand(follower, path7),
                new InstantCommand(()->robotBase.deployableRampSubsystem.undeploy()));
        //    new WaitCommand(800);
             /*   //new InstantCommand(() -> robotBase.intakeSubsystem.setPower(.5)),
                new FollowPathCommand(follower, pathFlower2),
                new FollowPathCommand(follower,path5),
                new WaitCommand(1000),
                new FollowPathCommand( follower,path6),
                new InstantCommand(() -> robotBase.deployableRampSubsystem.undeploy()),
                //new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new TransferCommand(robotBase),
                //new LaunchCommand(robotBase, 800),
                //new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path7));


             */

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
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }
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
