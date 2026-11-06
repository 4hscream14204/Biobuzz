package org.firstinspires.ftc.teamcode.Opmode.auto;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.paths.interpolator.Interpolator.constant;
import static com.pedropathing.paths.interpolator.Interpolator.linear;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.paths.Path;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.commands.TransferCommand;
import org.firstinspires.ftc.teamcode.commands.TurretDynamicVelocityCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;
import org.screamrobotics.SuperSCREAMLib.command.WaitUntilCommand;


@Autonomous(name = " CollabLeftFlowyAuto")
public class CollabLeftFlowerAuto extends OpMode {
    RobotBase robotBase;
    Follower follower;
    Path path;
    Path pathflower;
    Path path2;
    Path path3;
    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(61, 134,0);
    Pose upToFlower = poseFactory.of(45, 120, 90);
    Pose toFlower = poseFactory.of(45,126,90);
    Pose toShoot = poseFactory.of(59,116,0);
    Pose park = poseFactory.of(7,114, 0);

    ZoneControl zoneControl;
    SequentialCommandGroup commandGroup;
    PolygonZone robotZone = new PolygonZone(18, 18);

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        path = line(start, upToFlower).linear(start,upToFlower);
        pathflower =line(upToFlower, toFlower).linear(upToFlower,toFlower).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
        path2 = line(toFlower, toShoot).linear(toFlower,toShoot);
        path3 = line(toShoot, park).linear(toShoot,park);


        zoneControl = new ZoneControl(robotZone, follower);
        robotBase = new RobotBase(hardwareMap);

        commandGroup = new SequentialCommandGroup(
                //new LaunchCommand(robotBase,800),
                new WaitUntilCommand(()-> robotBase.cameraSubsystem.IsElevationUp()),
                //new WaitCommand(1000), //add tag
                new TransferCommand(robotBase, follower),
                //new InstantCommand(()->robotBase.intakeSubsystem.setPower(1)),
                new FollowPathCommand(follower, path),
                new FollowPathCommand(follower, pathflower),
                new WaitCommand(2000),
                new FollowPathCommand(follower, path2),
                //new InstantCommand(()->robotBase.intakeSubsystem.setPower(0)),
                new WaitUntilCommand(()-> robotBase.cameraSubsystem.IsElevationUp()),
                //new WaitCommand(15000), //add tag
                //new LaunchCommand(robotBase, 800),
                new TransferCommand(robotBase, follower),
                new FollowPathCommand(follower, path3)
        );


    }
    @Override
    public void start(){
        CommandScheduler.getInstance().schedule(
                commandGroup
        );
        CommandScheduler.getInstance().schedule(new TurretDynamicVelocityCommand(zoneControl, robotBase, follower));
    }

    @Override
    public void loop() {
        follower.update();
        robotBase.cameraSubsystem.updateCamera();
        CommandScheduler.getInstance().run();
        telemetry.addData("X: ", follower.pose().x());
        telemetry.addData("Y: ", follower.pose().y());
        telemetry.addData("Heading: ", follower.pose().heading());
        telemetry.addData("elevation", robotBase.cameraSubsystem.getElevation());
        telemetry.addData("April Tag", robotBase.cameraSubsystem.getTagName());
        telemetry.addData("Alliance: ", DataStorage.alliance);
        telemetry.update();
    }
}