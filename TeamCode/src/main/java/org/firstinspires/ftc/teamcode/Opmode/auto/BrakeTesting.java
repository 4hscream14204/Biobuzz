package org.firstinspires.ftc.teamcode.Opmode.auto;

import static com.pedropathing.api.Paths.line;




import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.AtomicPath;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;

@Autonomous (name = "How to Brake???")
public class BrakeTesting extends OpMode {
    PoseFactory posefactory = PoseFactory.degrees();
    Follower follower;
    Path path;
    Path path1;
    Pose start = posefactory.of(7,109,0);
    Pose end =posefactory.of(95,109,0);
    SequentialCommandGroup commandGroup;


    @Override
    public void init() {

        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
      //  path = line(start,end).linear(start,end);
      path1 = line(start,end).linear(start,end);

        commandGroup = new SequentialCommandGroup(
                new FollowPathCommand(follower,path));

    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(
                commandGroup
        );
    }

    @Override
    public void loop(){
        follower.update();
        CommandScheduler.getInstance().run();
        telemetry.addData("X: ", follower.pose().x());
        telemetry.addData("Y: ", follower.pose().y());
        telemetry.addData("Heading: ", follower.pose().heading());
        telemetry.update();
    }
}
