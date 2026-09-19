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
    PoseFactory posefactory  = PoseFactory.degrees();
    Pose start = posefactory.of(35, 8.5, 90);
    Pose midpoint = posefactory.of(35, 105, 90);
    Pose park = posefactory.of(15, 105, 90);
    Follower follower;
    Path path;
    Path path2;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
    path = line(start, midpoint).constant(start);
    path2 = line(midpoint, park).constant(midpoint);
    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(
                new FollowPathCommand(follower, path),
                new FollowPathCommand(follower, path2)
        );
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();
    }
}
