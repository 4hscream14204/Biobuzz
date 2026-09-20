package org.firstinspires.ftc.teamcode.opmode.auto;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.paths.Path.*;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;

@Autonomous
public class AutoFile extends OpMode {
    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(58.34096858638743, 9.51513416230366, 90);
    Pose mid = poseFactory.of(39.806282722513096, 30.476439790575917, 90);
    Pose shoot = poseFactory.of(57.96596858638743, 39.806282722513096, 90);
    Follower follower;
    Path path;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        path = curve(start, mid, shoot).constant(start);
    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(
                new FollowPathCommand(follower, path)
        );
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();
    }
}
