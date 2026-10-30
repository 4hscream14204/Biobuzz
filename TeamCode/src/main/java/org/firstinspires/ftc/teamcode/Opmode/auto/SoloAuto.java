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

@Autonomous (name = "SoloAuto")
public class SoloAuto extends OpMode {
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Path path1;
    Path path2;
    Path garden2;
    Path path3;
    Path pathFlower1;
    Path path4;
    Path path5;
    Path path6;
    Path path7;

    RobotBase robotBase;
    ZoneControl zoneControl;
    PolygonZone robotZone = new PolygonZone(18, 18);

    Pose start = poseFactory.of(63,8,180);
    Pose gardenBrake = poseFactory.of(9.5,8,90);
    Pose garden = poseFactory.of(10,8,180);
    Pose lineUpToHive = poseFactory.of(56,35,90);
    Pose shootFirstTime = poseFactory.of(56,119,90);
    Pose goUpToFirstFlower = poseFactory.of(46,128,90);
    Pose firstFlower = poseFactory.of(46,130,90);
    Pose secondFlower = poseFactory.of(15,47,180);
    Pose shootSecondTime = poseFactory.of(53,24,90);
    Pose park =poseFactory.of(9,95,90);
    Pose parkControl = poseFactory.of(9, 47, 90);

    SequentialCommandGroup commandGroup;
    @Override
    public void init(){
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        path1 = line(start,garden).linear(start,garden);
        garden2 = line(garden, gardenBrake).linear(garden,gardenBrake).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
        path2 = line(gardenBrake,lineUpToHive).linear(gardenBrake,lineUpToHive);
        path3 = line(lineUpToHive,shootFirstTime).linear(lineUpToHive,shootFirstTime);
        path4 = line(shootFirstTime,firstFlower).linear(shootFirstTime,firstFlower);
        //pathFlower1 = line()
        path5 = line(firstFlower, secondFlower).linear(firstFlower,secondFlower);
        path6 = line(secondFlower, shootSecondTime).linear(secondFlower,shootSecondTime);
        path7 = curve(shootSecondTime,parkControl, park).linear(shootSecondTime,park);

        follower.setPose(start);

        robotBase = new RobotBase(hardwareMap);
        zoneControl = new ZoneControl(robotZone, follower);

        commandGroup = new SequentialCommandGroup(
                //new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new TransferCommand(robotBase, follower),
                //new LaunchCommand(robotBase, 800),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path1),
                new FollowPathCommand(follower,path2),
                new FollowPathCommand(follower, garden2),
                new FollowPathCommand(follower,path3),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new TransferCommand(robotBase, follower),
                //new LaunchCommand(robotBase, 800),
                //new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path4),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new WaitCommand(800),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(.5)),
                new FollowPathCommand(follower,path5),
                new WaitCommand(1000),
                new FollowPathCommand( follower,path6),
                //new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new TransferCommand(robotBase, follower),
                //new LaunchCommand(robotBase, 800),
                //new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower,path7));

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
