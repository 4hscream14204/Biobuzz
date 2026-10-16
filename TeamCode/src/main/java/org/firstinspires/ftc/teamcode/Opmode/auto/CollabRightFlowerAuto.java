package org.firstinspires.ftc.teamcode.Opmode.auto;
import static com.pedropathing.api.Paths.line;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.commands.TransferBlockerCommand;
import org.firstinspires.ftc.teamcode.commands.TransferCommand;
import org.firstinspires.ftc.teamcode.commands.TurretDynamicVelocityCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.skeletonarmy.marrow.zones.PolygonZone;

@Autonomous (name = "CollabRightFlowyAuto")
public class CollabRightFlowerAuto extends OpMode {

    Follower follower;
    RobotBase robotBase;
    Path path;
    Path path2;
    Path path3;
    Path path4;

    ZoneControl zoneControl;

    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(61.8, 7.8, 180);
    Pose toFlower = poseFactory.of(9.5 , 42, 180);
    Pose toShoot = poseFactory.of(57, 23, 180);
    Pose parkHalf = poseFactory.of(27, 33, 180);
    Pose park = poseFactory.of(8.5, 98, 180);

    SequentialCommandGroup commandGroup;
    PolygonZone robotZone = new PolygonZone(18, 18);


    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        path = line(start, toFlower).constant(toFlower);
        path2 = line(toFlower, toShoot).constant(toFlower);
        path3 = line(toShoot, parkHalf).constant(parkHalf);
        path4 = line(parkHalf, park).constant(park);

        zoneControl = new ZoneControl(robotZone, follower);
        robotBase = new RobotBase(hardwareMap);

        commandGroup = new SequentialCommandGroup(
                //new LaunchCommand(robotBase,800),
                new WaitCommand(1000),
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(1)),
                new TransferBlockerCommand(robotBase, TransferBlocker.TransferBlockerPosition.RELEASE),
                new TransferCommand(robotBase, follower),
                new WaitCommand(500),
                new TransferBlockerCommand(robotBase, TransferBlocker.TransferBlockerPosition.STOP),
                new FollowPathCommand(follower, path),
                new FollowPathCommand(follower, path2),
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(0)),
                new WaitCommand(15000),
                //new LaunchCommand(robotBase, 800),
                new TransferBlockerCommand(robotBase, TransferBlocker.TransferBlockerPosition.RELEASE),
                new TransferCommand(robotBase, follower),
                new WaitCommand(500),
                new TransferBlockerCommand(robotBase, TransferBlocker.TransferBlockerPosition.STOP),
                new FollowPathCommand(follower, path3),
                new FollowPathCommand(follower, path4)
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
