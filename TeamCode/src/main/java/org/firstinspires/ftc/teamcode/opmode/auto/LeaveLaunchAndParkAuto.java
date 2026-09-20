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

    Pose start = poseFactory.of(55,8,180);
    //Pose launch =poseFactory.of(56,23,180);
    Pose flowerOne = poseFactory.of(10,46,180);
    Pose goingUnderHive = poseFactory.of(57,62,90);
    Pose goingUnderHiveCtrlPoint = poseFactory.of(57,31,90);



    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        path = line(start,flowerOne).constant(start);
      //  path2 = curve(flowerOne,goingUnderHiveCtrlPoint,goingUnderHive).linear(flowerOne,goingUnderHive);
        follower.setPose(start);
    }

    @Override
    public void start(){
        CommandScheduler.getInstance().schedule(
                new FollowPathCommand(follower, path)
             //   new FollowPathCommand(follower, path2)
            //    new FollowPathCommand(follower,path3)

        );


    }

    @Override
    public void loop(){
        follower.update();
        CommandScheduler.getInstance().run();
    }
}
