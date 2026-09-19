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
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
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
        follower = Constants.create(hardwareMap);
        //path = line
        CommandScheduler.getInstance().schedule(

                );
    }

    @Override
    public void loop() {

    }
}
