package org.firstinspires.ftc.teamcode.opmode.auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

import com.pedropathing.paths.Path;
@Autonomous (name = "CollabRightFlowyAuto")
public class CollabRightFlowyAuto extends OpMode {

    Follower follower;
    Path path;
    Path path2;
    Path path3;

    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(61.8, 7.8, 180);
    Pose toFlower = poseFactory.of(9.5 , 42, 180);
    Pose toShoot = poseFactory.of(57, 23, 180);
    Pose park = poseFactory.of(8.5, 98, 180);

    SequentialCommandGroup commandGroup;


    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        path = line(start, toFlower).constant(toFlower);
        path2 = line(toFlower, toShoot).constant(toFlower);
        path3 = line(toShoot, park).constant(park);

        commandGroup = new SequentialCommandGroup(
                new FollowPathCommand(follower, path),
                new FollowPathCommand(follower, path2)
          //      new WaitCommand(15000),
            //    new FollowPathCommand(follower, path3)
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
