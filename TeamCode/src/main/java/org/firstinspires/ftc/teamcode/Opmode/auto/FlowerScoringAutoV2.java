package org.firstinspires.ftc.teamcode.opmode.auto;

import static com.pedropathing.api.Paths.line;

import androidx.annotation.NonNull;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

@Autonomous (name = "FlowerScoringAuto")
public class FlowerScoringAutoV2 extends OpMode {
    Follower follower;
    Path path;
    Path path2;
    Path path3;
    Path path4;
    Path path5;
    Path path6;
    Path path7;
    Path path8;
    Path path9;

    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(62, 8, 180);
    Pose goGarden = poseFactory.of(5, 9, 180);
    Pose shootOne = poseFactory.of(57, 9, 180);
    Pose goNearFlower = poseFactory.of(24, 48, 180);
    Pose goFlower = poseFactory.of(12, 48, 180);
    Pose goAround = poseFactory.of(24,94, 180);
    Pose shootTwo = poseFactory.of(59, 112, 180);
    Pose goFlowerTwo = poseFactory.of(55, 134, 90);
    Pose shootThree = poseFactory.of(59, 112, 180);
    Pose park = poseFactory.of(4, 108, 180);

    SequentialCommandGroup commandGroup;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        path = line(start, goGarden).constant(start);
        path2 = line(goGarden, shootOne).constant(goGarden);
        path3 = line(shootOne, goNearFlower).constant(shootOne);
        path4 = line(goNearFlower, goFlower).constant(goNearFlower);
        path5 = line(goFlower, goAround).constant(goFlower);
        path6 = line(goAround,shootTwo).constant(goAround);
        path7 = line(shootTwo, goFlowerTwo).linear(shootTwo,goFlowerTwo);
        path8 = line(goFlowerTwo, shootThree).linear(goFlowerTwo, shootThree);
        path9 = line(shootThree, park).constant(shootThree);

        commandGroup = new SequentialCommandGroup(
                new WaitCommand(1000),
                new FollowPathCommand(follower, path),
                new FollowPathCommand(follower, path2),
                new WaitCommand(1000),
                new FollowPathCommand(follower, path3),
                new FollowPathCommand(follower, path4),
                new WaitCommand(2000),
                new FollowPathCommand(follower, path5),
                new FollowPathCommand(follower, path6),
                new WaitCommand(1000),
                new FollowPathCommand(follower, path7),
                new WaitCommand(2000),
                new FollowPathCommand(follower, path8),
                new WaitCommand(1000),
                new FollowPathCommand(follower, path9)
        );
    }

    @Override
    public void start() {
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
