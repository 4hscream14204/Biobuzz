package org.firstinspires.ftc.teamcode.opmode.auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;

import com.pedropathing.paths.Path.*;

@Autonomous(name = "LeavePark")
public class LeaveParkAuto extends OpMode {
    PoseFactory pf = PoseFactory.degrees();
    Pose start = pf.of(35, 8.5, 90);
    Pose park = pf.of(35, 105, 90);
    Follower follower;
    Path path;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        path = line(start, park).linear(start, park);
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
