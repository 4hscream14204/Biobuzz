package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import static com.pedropathing.api.Paths.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

@Autonomous(name = "Thwimp Auto")
public class ThwimpAuto extends OpMode {

    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start;
    Pose shoot;
    Pose otherSide;
    Path startToShoot;
    Path shootToOtherSide;
    SequentialCommandGroup commandGroup;
    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        start = poseFactory.of(40, 8, 90);
        shoot = poseFactory.of(16, 67, 0);
        otherSide = poseFactory.of(113, 67, 0);

        startToShoot = line(start, shoot).linear(start, shoot);
        shootToOtherSide = line(shoot, otherSide).constant(shoot);

        commandGroup = new SequentialCommandGroup(
                new FollowPathCommand(follower, startToShoot),
                new WaitCommand(2000),
                new FollowPathCommand(follower, shootToOtherSide)
        );

    }

    @Override
    public void start() {
        follower.setPose(start);
        commandGroup.schedule();
    }

    @Override
    public void loop() {
        follower.update();
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        CommandScheduler.getInstance().run();
    }

}
