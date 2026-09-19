package org.firstinspires.ftc.teamcode.opmode.auto;

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

import static com.pedropathing.api.Paths.*;

@Autonomous(name = "Six Wheel Auto")
public class SixWheelAutoTest extends OpMode {
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(9, 103, 0);
    private final Pose path1Start = poseFactory.of(9, 103, 0);
    private final Pose path1 = poseFactory.of(64, 13, 90);
    private final Pose path1Control1 = poseFactory.of(32.0858, 14.4799, 0);
    private final Pose throughHive = poseFactory.of(64, 120, 270);
    Path path;
    Path path2;
    SequentialCommandGroup commandGroup;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        path = curve(path1Start, path1Control1, path1).linear(path1Start, path1);
        path2 = line(path1, throughHive).linear(path1, throughHive);
        follower.setPose(start);

        commandGroup = new SequentialCommandGroup(
                new FollowPathCommand(follower, path),
                new FollowPathCommand(follower, path2)
        );
    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(commandGroup);
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
    }
}
