package org.firstinspires.ftc.teamcode.opmode.auto;

import static com.pedropathing.api.Paths.curve;
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


@Autonomous (name = "GardenAutoForTheWin")
public class GardenAutoForTheWin extends OpMode {

    Follower follower;
    Path path;
    Path path2;
    Path path3;
    Path path4;
    Path path5;

    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(58.8,8.6,90);
    Pose goToGarden = poseFactory.of(6.9, 8.1, 0);
    Pose otherSide = poseFactory.of(46.5, 117, 270);
    Pose otherSideControlPoint = poseFactory.of(2.29, 81.81, 270);
    Pose toTheFlower = poseFactory.of(53.2, 136.7, 0);
    Pose shoot = poseFactory.of(54, 125.9, 270);
    Pose park = poseFactory.of(6.2, 114.6, 90);
    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        path = line(start, goToGarden).linear(start,goToGarden);
        path2 = curve(goToGarden, otherSide, otherSideControlPoint).linear(goToGarden, otherSide);
        path3 = line(otherSide, toTheFlower).linear(otherSide, toTheFlower);
        path4 = line(toTheFlower, shoot).linear(toTheFlower, shoot);
        path5 = line(shoot, park).linear(shoot, park);
    }
    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(
                new FollowPathCommand(follower, path),
                new FollowPathCommand(follower, path2),
                new FollowPathCommand(follower, path3),
                new FollowPathCommand(follower, path4),
                new FollowPathCommand(follower, path5)
        );
    }
    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();
    }

}
