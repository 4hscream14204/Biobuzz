package org.firstinspires.ftc.teamcode.pedro.Routes;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import static com.pedropathing.api.Paths.line;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;

@Autonomous
public class ParkAndLeave extends OpMode {
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose start = p.of(35, 8.5, 90);
    private final Pose park = p.of(15, 105, 90);
    private Follower follower;

    @Override
    public void init(){
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
    }

    @Override
    public void start(){
        CommandScheduler.getInstance().schedule(new FollowPathCommand(follower, line(start, park)));
    }
    @Override
    public void loop(){
        follower.update();
        CommandScheduler.getInstance().run();
    }
}
