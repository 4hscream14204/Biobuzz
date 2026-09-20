package org.firstinspires.ftc.teamcode.pedro.Routes;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;

@Autonomous
public class ParkAndLeave extends OpMode {
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(24, 8.5, 90);
    private final Pose pathStart = poseFactory.of(24, 8.5, 90);
    private final Pose controlPath = poseFactory.of(10, 74, 90);
    private final Pose park = poseFactory.of(35, 111, 90);
   // private final Pose end = poseFactory.of(79, 117, 0);
    private Follower follower;
    Path path;

    @Override
    public void init(){
        CommandScheduler.getInstance().reset();
        //follower = Constants.create(hardwareMap);
        path = curve(pathStart, controlPath, park).constant(pathStart);
        follower.setPose(start);
    }

    @Override
    public void start(){
        CommandScheduler.getInstance().schedule(
                new FollowPathCommand(follower, path)
        );
    }

    @Override
    public void loop(){
        follower.update();
        CommandScheduler.getInstance().run();
        telemetry.addData("Heading: ", follower.pose().heading());
        telemetry.addData("X: ", follower.pose().x());
        telemetry.addData("Y: ", follower.pose().y());
        telemetry.update();
    }
}
