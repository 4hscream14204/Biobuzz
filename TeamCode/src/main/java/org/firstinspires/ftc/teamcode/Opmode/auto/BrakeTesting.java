package org.firstinspires.ftc.teamcode.Opmode.auto;

import static com.pedropathing.api.Paths.line;


import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

@Autonomous (name = "How to Brake???")
public class BrakeTesting extends OpMode {
    Foresight foresight;
    RobotBase robotBase;
    Follower follower;
    Path path;

    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(7,109,0);
    Pose end =poseFactory.of(72,109,0);

    SequentialCommandGroup commandGroup;

    @Override
    public void init(){
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        path = line(start,end).linear(start,end).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
        robotBase = new RobotBase(hardwareMap);

        commandGroup = new SequentialCommandGroup(
                new FollowPathCommand(follower, path)
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





