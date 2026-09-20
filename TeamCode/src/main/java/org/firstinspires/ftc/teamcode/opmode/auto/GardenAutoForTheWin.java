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
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;


@Autonomous (name = "GardenAutoForTheWin")
public class GardenAutoForTheWin extends OpMode {

    Follower follower;
    Path path;
    Path path2;
    Path path3;
    Path path4;
    Path path5;
    Path path6;

    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(55.2,8.1,180);
    Pose goToGarden = poseFactory.of(13, 8.1, 180);
    Pose otherSide = poseFactory.of(35, 117, 270);
    Pose toTheFlower = poseFactory.of(53.2, 136.7, 0);
    Pose shoot = poseFactory.of(54, 125.9, 270);
    Pose park = poseFactory.of(6.2, 114.6, 90);


    SequentialCommandGroup commandGroup;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        path = line(start, goToGarden).linear(start,goToGarden);
        path2 = line(goToGarden, otherSide).linear(goToGarden, otherSide);
        path3 = line(otherSide, toTheFlower).linear(otherSide, toTheFlower);
        path4 = line(toTheFlower, shoot).linear(toTheFlower, shoot);
        path5 = line(shoot, park).linear(shoot, park);

        commandGroup = new SequentialCommandGroup(
                new FollowPathCommand(follower, path),
                new WaitCommand(1000),
                new FollowPathCommand(follower, path2),
                new FollowPathCommand(follower, path3),
                new WaitCommand(500),
                new FollowPathCommand(follower, path4),
                new WaitCommand(1000),
                new FollowPathCommand(follower, path5)
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
