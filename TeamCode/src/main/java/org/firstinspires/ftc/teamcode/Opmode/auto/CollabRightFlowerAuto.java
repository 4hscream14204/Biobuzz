package org.firstinspires.ftc.teamcode.Opmode.auto;
import static com.pedropathing.api.Paths.line;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.commands.TransferCommand;
import org.firstinspires.ftc.teamcode.commands.TurretDynamicVelocityCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

import com.pedropathing.paths.Path;
import com.skeletonarmy.marrow.zones.PolygonZone;

@Autonomous(name = "CollabRightFlowerAuto")
public class CollabRightFlowerAuto extends OpMode {

    Follower follower;
    RobotBase robotBase;
    Path path;
    Path slowGarden;
    Path path2;
    Path path3;
    Path path4;
    Path path5;
    Path path6;
    Path flowerLineUp;

    ZoneControl zoneControl;

    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(61.8, 7.8, 180);
    Pose toGarden = poseFactory.of(5 , 9, 180);
    Pose gardenBrake = poseFactory.of(9,8,180);
    Pose toFlower = poseFactory.of(19 , 44, 180);
    Pose lineUpToFlower =poseFactory.of(9,43,180);
    Pose toShoot1 = poseFactory.of(57, 23, 180);
    Pose toShoot2 = poseFactory.of(57, 23, 180);
    Pose parkHalf = poseFactory.of(27, 33, 180);
    Pose park = poseFactory.of(8.5, 98, 180);

    SequentialCommandGroup commandGroup;
    PolygonZone robotZone = new PolygonZone(18, 18);


    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        path = line(start, toGarden).constant(toGarden).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
    //    slowGarden = line(toGarden,gardenBrake).constant(toGarden).with(Constants.foresightConfig.maxPathSpeed.at(0.3));
        path2 = line(toGarden, toShoot1).constant(toShoot1);
        path3 = line(toShoot1, toFlower).constant(toFlower);
        flowerLineUp =line(toFlower,lineUpToFlower).constant(toFlower).with(Constants.foresightConfig.maxPathSpeed.at(0.3));
        path4 = line(lineUpToFlower, toShoot2).constant(toShoot2);
        path5 = line(toShoot2, parkHalf).constant(parkHalf);
        path6 = line(parkHalf, park).constant(park);

        zoneControl = new ZoneControl(robotZone, follower);
        robotBase = new RobotBase(hardwareMap);

        commandGroup = new SequentialCommandGroup(
                //new LaunchCommand(robotBase,800),
                new WaitCommand(800),
                new TransferCommand(robotBase, follower),
                //new InstantCommand(()->robotBase.intakeSubsystem.setPower(1)),
                new FollowPathCommand(follower, path),
                //new WaitCommand(2000),
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(1)),
              //  new FollowPathCommand(follower,flowerLineUp),
                new FollowPathCommand(follower, path2),
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(0.5)),
                new WaitCommand(1000),
                //new LaunchCommand(robotBase, 800),
                new TransferCommand(robotBase, follower),
                new FollowPathCommand(follower, path3),
                new FollowPathCommand(follower, flowerLineUp),
                new WaitCommand(2000),
                new FollowPathCommand(follower, path4),
                new TransferCommand(robotBase, follower),
                new FollowPathCommand(follower, path5),
                new FollowPathCommand(follower, path6)


        );


    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(
                commandGroup
        );
        CommandScheduler.getInstance().schedule(new TurretDynamicVelocityCommand(zoneControl, robotBase, follower));
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


