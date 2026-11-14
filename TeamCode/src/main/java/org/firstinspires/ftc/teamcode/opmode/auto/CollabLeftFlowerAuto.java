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
import org.firstinspires.ftc.teamcode.subsystems.DeployableRamp;
import org.firstinspires.ftc.teamcode.subsystems.DistanceSensor;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
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
    Path brakeToFlower;
    Path flowerToShoot;
    Path shootToPark;
    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(58,133,0);
    Pose almostFlower = poseFactory.of(46, 116, 90);
    Pose toFlower = poseFactory.of(46,126.5,90);
    Pose toShoot = poseFactory.of(62,116,90);
    Pose park = poseFactory.of(8.5,114, 0);

    ZoneControl zoneControl;
    SequentialCommandGroup commandGroup;
    PolygonZone robotZone = new PolygonZone(18, 18);

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        startToFlower = line(start, almostFlower).linear(start, almostFlower);
        brakeToFlower = line(almostFlower, toFlower).constant(toFlower).with(Constants.foresightConfig.maxPathSpeed.at(0.2));;
        flowerToShoot = line(toFlower, toShoot).constant(toFlower).with(Constants.foresightConfig.maxPathSpeed.at(0.8));;
        shootToPark = line(toShoot, park).linear(toShoot,park);

        zoneControl = new ZoneControl(robotZone, follower);
        robotBase = new RobotBase(hardwareMap);

        robotBase.tranferBlockersubsystem.setPosition(TransferBlocker.TransferBlockerPosition.STOP);
        robotBase.deployableRampSubsystem.setPosition(DeployableRamp.DeployableRampPosition.AWAY);


        commandGroup = new SequentialCommandGroup(
                //new LaunchCommand(robotBase,800),
               // new InstantCommand(()->robotBase.launcherSubsystem.setVelocity(900)),
                new WaitCommand(3000),
                new TransferCommand(robotBase),
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(-1)),
                new FollowPathCommand(follower, startToFlower),
                new InstantCommand(()->robotBase.deployableRampSubsystem.deploy()),
                new FollowPathCommand(follower, brakeToFlower),
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(-1)),
                new WaitCommand(2000),
                new FollowPathCommand(follower, flowerToShoot),
                new WaitCommand(10000),
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
        CommandScheduler.getInstance().run();
        telemetry.addData("X: ", follower.pose().x());
        telemetry.addData("Y: ", follower.pose().y());
        telemetry.addData("Heading: ", follower.pose().heading());
        telemetry.update();
    }
}