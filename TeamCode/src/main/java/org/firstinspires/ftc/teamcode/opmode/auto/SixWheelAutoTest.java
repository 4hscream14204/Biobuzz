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

import static com.pedropathing.api.Paths.*;

@Autonomous(name = "Six Wheel Auto")
public class SixWheelAutoTest extends OpMode {
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(9, 103, 0);
    private final Pose path1Start = poseFactory.of(9, 103, 0);
    private final Pose path1 = poseFactory.of(59, 13, 90);
    private final Pose path1Control1 = poseFactory.of(32.0858, 14.4799, 0);
    Path path;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        path = curve(path1Start, path1Control1, path1).linear(path1Start, path1);
        follower.setPose(start);
    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(new FollowPathCommand(follower, path));
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();
    }
}
