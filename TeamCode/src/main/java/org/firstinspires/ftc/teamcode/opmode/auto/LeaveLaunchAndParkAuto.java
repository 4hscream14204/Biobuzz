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
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

@Autonomous (name = "FlowerAutoForTheWinnn")
public class LeaveLaunchAndParkAuto extends OpMode {
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Path path;
    Path path2;
    Path path3;
Path path4;
Path path5;
    Pose start = poseFactory.of(55,8,180);
    //Pose launch =poseFactory.of(56,23,180);
    Pose flowerOne = poseFactory.of(16,43,180);
    Pose goingUnderHive = poseFactory.of(56,38,90);

    Pose positionToShoot = poseFactory.of(51,117,90);
    Pose flowerTwo = poseFactory.of(35, 124,90);
    Pose parking = poseFactory.of(8, 120, 90);


    SequentialCommandGroup commandGroup;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        path = line(start,flowerOne).linear(start,flowerOne);
        path2 = line(flowerOne,goingUnderHive).linear(flowerOne,goingUnderHive);
        path3 = line(goingUnderHive,positionToShoot).linear(goingUnderHive,positionToShoot);
        path4 = line(positionToShoot, flowerTwo).linear(positionToShoot,flowerTwo);
        path5 = line(flowerTwo,parking).linear(flowerTwo,parking);

        follower.setPose(start);

        commandGroup = new SequentialCommandGroup(
                new FollowPathCommand(follower, path),
                new WaitCommand(1000),
                new FollowPathCommand(follower, path2),
                 new FollowPathCommand(follower,path3),
                new WaitCommand(1000),
                new FollowPathCommand(follower,path4),
                new WaitCommand(1000),
                new FollowPathCommand(follower,path5)

        );
    }

    @Override
    public void start(){
        CommandScheduler.getInstance().schedule(
               commandGroup

        );


    }

    @Override
    public void loop(){
        follower.update();
        CommandScheduler.getInstance().run();
        telemetry.addData("Heading: ", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Position X: ", follower.pose().x());
        telemetry.addData("Position Y: ", follower.pose().y());
    }
}
