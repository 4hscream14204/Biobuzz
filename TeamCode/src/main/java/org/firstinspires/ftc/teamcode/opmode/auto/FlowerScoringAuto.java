package org.firstinspires.ftc.teamcode.opmode.auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;


@Autonomous(name = "FlowerScoring")
public class FlowerScoringAuto extends OpMode {
    Follower follower;
    Path path;
    Path path2;
    Path path3;
    Path path4;
    Path path5;
    Path path6;
    Path path7;
    Path path8;
    PoseFactory posefactory = PoseFactory.degrees();
    Pose Start = posefactory.of(56, 8, 180);
    Pose ShootFirst = posefactory.of(57,23,180);
    Pose GoToFlowerFirst = posefactory.of(10,47, 180);
    Pose GoToFlowerSecond = posefactory.of(48, 133, 90);
    Pose ShootSecond = posefactory.of(57, 118, 180);
    Pose GotToOtherSide = posefactory.of(57,8,180);
    Pose GoToGarden = posefactory.of(4,8,180);
    Pose ShootThird = posefactory.of(57,15,180);
    Pose GoToEndPose = posefactory.of(10,99,90);

    SequentialCommandGroup commandGroup;

    @Override
    public void init(){
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(Start);
        path = line(Start,ShootFirst).linear(Start,ShootFirst);
        path2 = line(ShootFirst, GoToFlowerFirst).linear(ShootFirst, GoToFlowerFirst);
        path3 = line(GoToFlowerFirst,GoToFlowerSecond).linear(GoToFlowerFirst,GoToFlowerSecond);
        path4 = line(GoToFlowerSecond,ShootSecond).linear(GoToFlowerSecond,ShootSecond);
        path5 = line(ShootSecond, GotToOtherSide).linear(ShootSecond, GotToOtherSide);
        path6 = line(GotToOtherSide, GoToGarden).linear(GotToOtherSide, GoToGarden);
        path7 = line(GoToGarden, ShootThird).linear(GoToGarden, ShootThird);
        path8 = line(ShootThird, GoToEndPose).linear(ShootThird, GoToEndPose);

        commandGroup = new SequentialCommandGroup(
                new WaitCommand(500),
                new FollowPathCommand(follower, path),
                new WaitCommand(1000),
                new FollowPathCommand(follower, path2),
                new FollowPathCommand(follower, path3),
        new FollowPathCommand(follower, path4),

        new FollowPathCommand(follower, path5),

        new FollowPathCommand(follower, path6),
        new FollowPathCommand(follower, path7),

        new FollowPathCommand(follower, path8)
        );

    }
    @Override
    public void start(){
        CommandScheduler.getInstance().schedule(
                commandGroup
        );
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();
        telemetry.addData("X: ", follower.pose().x());
        telemetry.addData("Y: ", follower.pose().y());
        telemetry.addData("Heading: ", follower.pose().heading());
        telemetry.update();
    }
}

