package org.firstinspires.ftc.teamcode.opmode.auto;

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


@Autonomous(name = " CollabLeftFlowyAuto")
public class CollabLeftFlowerAuto extends OpMode {
    Follower follower;
    Path path;
    Path path2;
    Path path3;
    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(64,138,0);
    Pose lineUpToFlower = poseFactory.of(49,125,90);
    Pose shootPollen = poseFactory.of(59,116,0);
    Pose park = poseFactory.of(7,114, 0);

    SequentialCommandGroup commandGroup;

    @Override
    public void init(){
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        path = line(start,lineUpToFlower).linear(start,lineUpToFlower);
        path2 = line(lineUpToFlower, shootPollen).linear(lineUpToFlower, shootPollen);
        path3 = line(shootPollen,park).linear(shootPollen,park);


        commandGroup = new SequentialCommandGroup(
                new WaitCommand(500),
                new FollowPathCommand(follower, path),
                new WaitCommand(1000),
                new FollowPathCommand(follower, path2),
                new FollowPathCommand(follower, path3)
        );

    }
    @Override
    public void start(){
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
