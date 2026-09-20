package org.firstinspires.ftc.teamcode.opmode.auto;

import static com.pedropathing.api.Paths.curve;
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

@Autonomous (name = "FlowerAutoForTheWinnn")
public class LeaveLaunchAndParkAuto extends OpMode {
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Path path;
    Path path2;
    Path path3;

    Pose start = poseFactory.of(55,8,90);
    Pose launch =poseFactory.of(9,45,90);
    Pose flowerOne = poseFactory.of(8,45,0);



    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        path = line(start,launch).constant(start);
        path2 = line(launch, flowerOne).linear(launch, flowerOne);
        follower.setPose(start);
    }

    @Override
    public void start(){
        CommandScheduler.getInstance().schedule(
                new FollowPathCommand(follower, path),
                new FollowPathCommand(follower, path2)
        );


    }

    @Override
    public void loop(){
        follower.update();
        CommandScheduler.getInstance().run();
    }
}
