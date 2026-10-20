package org.firstinspires.ftc.teamcode.opmode.auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

@Autonomous (name = "SoloAuto")
public class SoloAuto extends OpMode {
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Path path1;
    Path path2;
    Path path3;
    Path path4;
    Path path5;
    Path path6;
    Path path7;

    RobotBase robotBase;

    Pose start = poseFactory.of(63,8,180);
    Pose garden = poseFactory.of(2,8,180);
    Pose lineUpToHive = poseFactory.of(56,35,90);
    Pose shootFirstTime = poseFactory.of(56,119,90);
    Pose firstFlower = poseFactory.of(46,130,90);
    Pose secondFlower = poseFactory.of(9,47,180);
    Pose shootSecondTime = poseFactory.of(53,24,90);
    Pose park =poseFactory.of(9,95,90);

    SequentialCommandGroup commandGroup;
    @Override
    public void init(){
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        path1 = line(start,garden).linear(start,garden);
        path2 =line(garden,lineUpToHive).linear(garden,lineUpToHive);
        path3 = line(lineUpToHive,shootFirstTime).linear(lineUpToHive,shootFirstTime);
        path4 = line(shootFirstTime,firstFlower).linear(shootFirstTime,firstFlower);
        path5 = line(firstFlower, secondFlower).linear(firstFlower,secondFlower);
        path6 = line(secondFlower, shootSecondTime).linear(secondFlower,shootSecondTime);
        path7 = line(shootSecondTime,park).linear(shootSecondTime,park);

        follower.setPose(start);

        robotBase = new RobotBase(hardwareMap);

        commandGroup = new SequentialCommandGroup(
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new LaunchCommand(robotBase, 800),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path1),
                new FollowPathCommand(follower,path2),
                new FollowPathCommand(follower,path3),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new LaunchCommand(robotBase, 800),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path4),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new WaitCommand(800),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path5),
                new FollowPathCommand( follower,path6),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new LaunchCommand(robotBase, 800),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path7));

    }

    @Override
    public void start(){
        CommandScheduler.getInstance().schedule(
                commandGroup
        );
    }

    @Override
    public void loop(){
        follower.update();
        CommandScheduler.getInstance().run();
        telemetry.addData("X: ", follower.pose().x());
        telemetry.addData("Y: ", follower.pose().y());
        telemetry.addData("Heading: ", follower.pose().heading());
        telemetry.update();
    }

}
