package org.firstinspires.ftc.teamcode.pedro;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;

@Autonomous(name = "FlowerScoring")
public class FlowerScoringAuto extends OpMode {
    PoseFactory posefactory = PoseFactory.degrees();
    Pose Start = posefactory.of(56, 8, 180);
    Pose ShootFirst = posefactory.of(57,23,180);
    Pose GoToFlowerFirst = posefactory.of(10,47, 180);
    Pose GoToFlowerSecond = posefactory.of(48, 133, 90);
    Pose ShootSecond = posefactory.of(57, 118, 180);
    Pose GotToOtherSide = posefactory.of(57,8,180);
    Pose GoToGarden = posefactory.of(4,8,180);
    Pose ShootThird = posefactory.of(57,15,180);
    Pose GoToEndPose = posefactory.of(10,99,90);
    Follower follower;
    Path path;
    Path path2;
    Path path3;
    Path path4;
    Path path5;
    Path path6;
    Path path7;
    Path path8;

    @Override
    public void init(){
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(Start);
        path = line(Start, ShootFirst).constant(Start);
        path2 = line(ShootFirst, GoToFlowerFirst).constant(ShootFirst);
        path3 = line(GoToFlowerFirst, GoToFlowerSecond).linear(GoToFlowerFirst, GoToFlowerSecond);
        path4 = line(GoToFlowerSecond, ShootSecond).linear(GoToFlowerSecond, ShootSecond);
        path5 = line(ShootSecond, GotToOtherSide).constant(ShootSecond);
        path6 = line(GotToOtherSide, GoToGarden).constant(GotToOtherSide);
        path7 = line(GoToGarden, ShootThird).constant(GoToGarden);
        path8 = line(ShootThird, GoToEndPose).linear(ShootThird, GoToEndPose);


    }
    @Override
    public void start(){
        CommandScheduler.getInstance().schedule();
        new FollowPathCommand(follower, path);
        new FollowPathCommand(follower, path2);
        new FollowPathCommand(follower, path3);
        new FollowPathCommand(follower, path4);
        new FollowPathCommand(follower, path5);
        new FollowPathCommand(follower, path6);
        new FollowPathCommand(follower, path7);
        new FollowPathCommand(follower, path8);
    }
@Override
    public void loop(){
follower.update();
CommandScheduler.getInstance().run();
}
}
