package org.firstinspires.ftc.teamcode.Opmode.auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.DeployableRamp;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;

@Autonomous(name = "LeaveParkLeft")
public class LeaveParkAuotLeft extends OpMode {
    RobotBase robotBase;
    PoseFactory posefactory  = PoseFactory.degrees();
    Pose start = posefactory.of(32 , 156, 0);
    //   Pose midpoint = posefactory.of(35, 105, 90);
    Pose park = posefactory.of(12, 141, 0);
    Follower follower;
    Path path;
    Path path2;

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        CommandScheduler.getInstance().reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        path = line(start, park).constant(start);
        //  path2 = line(midpoint, park).constant(midpoint);
        robotBase.deployableRampSubsystem.setPosition(DeployableRamp.DeployableRampPosition.AWAY);
    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(
                new FollowPathCommand(follower, path)
                //   new FollowPathCommand(follower, path2)
        );
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void stop() {
        DataStorage.position = follower.pose();
    }
}


