package org.firstinspires.ftc.teamcode.Opmode.auto;
import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.paths.Path;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.commands.TransferCommand;
import org.firstinspires.ftc.teamcode.commands.TurretDynamicVelocityCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.DistanceSensor;
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
    Path startToFlower;
    Path flowerToShoot;
    Path shootToPark;
    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(64,138,0);
    Pose toFlower = poseFactory.of(49,125,90);
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
        startToFlower = line(start, toFlower).constant(toFlower);
        flowerToShoot = line(toFlower, toShoot).constant(toFlower);
        shootToPark = line(toShoot, park).constant(park);


        zoneControl = new ZoneControl(robotZone, follower);
        robotBase = new RobotBase(hardwareMap);

        commandGroup = new SequentialCommandGroup(
                //new LaunchCommand(robotBase,800),
                new WaitCommand(1000),
                new TransferCommand(robotBase),
                //new InstantCommand(()->robotBase.intakeSubsystem.setPower(1)),
                new FollowPathCommand(follower, startToFlower),
                new InstantCommand(()->robotBase.deployableRampSubsystem.deploy()),
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(1)),
                new WaitUntilCommand(()->robotBase.distanceSensorSubsystem.hasFourPollen()).withTimeout(800),
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(0.5)),
                new FollowPathCommand(follower, flowerToShoot),
                //new InstantCommand(()->robotBase.intakeSubsystem.setPower(0)),
                new WaitCommand(15000),
                //new LaunchCommand(robotBase, 800),
                new TransferCommand(robotBase),
                new FollowPathCommand(follower, shootToPark)
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
        robotBase.distanceSensorSubsystem.update();
        CommandScheduler.getInstance().run();
        telemetry.addData("X: ", follower.pose().x());
        telemetry.addData("Y: ", follower.pose().y());
        telemetry.addData("Heading: ", follower.pose().heading());
        telemetry.update();
    }
}