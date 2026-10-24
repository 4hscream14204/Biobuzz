package org.firstinspires.ftc.teamcode.Opmode.auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.commands.TransferCommand;
import org.firstinspires.ftc.teamcode.commands.TurretDynamicVelocityCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

@Autonomous (name = "SoloAutoAround")
public class SoloAutoAround extends OpMode {
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Path path1;
    Path path2;
    Path path3;
    Path path4;
    Path path5;
    Path path6;
    Path path7;
    Path path8;
    Path path9;
    Path goToFlower1;
    Path goToFlower2;

    RobotBase robotBase;
    ZoneControl zoneControl;
    PolygonZone robotZone = new PolygonZone(18, 18);

    Pose start = poseFactory.of(63,8,180);
    Pose garden = poseFactory.of(9.5,8,180);
    Pose lineUpToHive = poseFactory.of(56,35,90);

    Pose sideHive1 = poseFactory.of(24, 36, 90);
    Pose sideHive2 = poseFactory.of(24, 94, 90);
    Pose shootFirstTime = poseFactory.of(56,119,90);
    Pose firstFlower = poseFactory.of(46,128,90);
    Pose goUpToPollen = poseFactory.of(46, 130, 90);
    Pose secondFlower = poseFactory.of(15,47,180);
    Pose goUpToPollenTwo = poseFactory.of(9, 47, 180);
    Pose shootSecondTime = poseFactory.of(53,24,90);
    Pose park = poseFactory.of(9,95,90);

    SequentialCommandGroup commandGroup;
    @Override
    public void init(){
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        path1 = line(start,garden).linear(start,garden).with(Constants.foresightConfig.maxPathSpeed.at(0.7));
        path2 = line(garden,lineUpToHive).linear(garden,lineUpToHive);
        path3 = line(lineUpToHive, sideHive1).constant(sideHive1);
        path4 = line(sideHive1, sideHive2).constant(sideHive2);
        path5 = line(sideHive2,shootFirstTime).linear(lineUpToHive,shootFirstTime);
        path6 = line(shootFirstTime,firstFlower).linear(shootFirstTime,firstFlower);
        goToFlower1 = line(firstFlower, goUpToPollen).linear(firstFlower, goUpToPollen).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
        path7 = line(firstFlower, secondFlower).linear(firstFlower,secondFlower);
        goToFlower2 = line(secondFlower, goUpToPollenTwo).constant(secondFlower).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
        path8 = line(secondFlower, shootSecondTime).linear(secondFlower,shootSecondTime);
        path9 = line(shootSecondTime,park).linear(shootSecondTime,park);


        follower.setPose(start);

        robotBase = new RobotBase(hardwareMap);
        zoneControl = new ZoneControl(robotZone, follower);

        commandGroup = new SequentialCommandGroup(
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(0)),
                new TransferCommand(robotBase, follower),
                new FollowPathCommand(follower,path1),
                new FollowPathCommand(follower,path2),
                new FollowPathCommand(follower,path3),
                new FollowPathCommand(follower,path4),
                new FollowPathCommand(follower,path5),
                new TransferCommand(robotBase, follower),
                new FollowPathCommand(follower,path6),
                new FollowPathCommand(follower,goToFlower1),
                new WaitCommand(2000),
                new FollowPathCommand(follower,path7),
                new FollowPathCommand(follower,goToFlower2),
                new WaitCommand(2000),
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path8),
                new FollowPathCommand(follower,path9)

        );

















            /*    //new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new TransferCommand(robotBase, follower),
                //new LaunchCommand(robotBase, 800),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path1),
                new FollowPathCommand(follower,path2),
                new FollowPathCommand(follower,path3),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new TransferCommand(robotBase, follower),
                //new LaunchCommand(robotBase, 800),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path4),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new WaitCommand(800),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(.5)),
                new FollowPathCommand(follower,path5),
                new FollowPathCommand( follower,path6),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new TransferCommand(robotBase, follower),
                //new LaunchCommand(robotBase, 800),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path7)); */

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

}
