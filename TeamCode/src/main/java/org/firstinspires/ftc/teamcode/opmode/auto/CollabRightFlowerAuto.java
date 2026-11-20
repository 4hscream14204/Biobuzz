package org.firstinspires.ftc.teamcode.opmode.auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.commands.TransferCommand;
import org.firstinspires.ftc.teamcode.commands.TurretDynamicVelocityCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.DeployableRamp;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

import java.util.List;

@Autonomous(name = "CollabRightFlowerAuto")
public class  CollabRightFlowerAuto extends OpMode {

    Follower follower;
    RobotBase robotBase;
    Path startToGarden;
    Path slowGarden;
    Path gardenToShoot1;
    Path shoot1ToFlower;
    Path flowerLineUp;
    Path flowerToShoot2;
    Path shootToParkHalf;
    Path parkFully;

    ZoneControl zoneControl;

    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(51, 6, 180);
    Pose toGarden = poseFactory.of(20, 6, 180);
    Pose gardenBrake = poseFactory.of(8,6,180);
    Pose toFlower = poseFactory.of(27 , 46, 180);
    Pose lineUpToFlower =poseFactory.of(12,46,180);
    Pose toShoot1 = poseFactory.of(57, 13, 180);
    Pose toShoot2 = poseFactory.of(57, 13, 180);
    Pose parkHalf = poseFactory.of(27, 33, 180);
    Pose park = poseFactory.of(9, 95, 0);

    SequentialCommandGroup commandGroup;
    PolygonZone robotZone = new PolygonZone(18, 18);

    List<LynxModule> allHubs;

    @Override
    public void init() {

        allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        CommandScheduler.getInstance().reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        startToGarden = line(start, toGarden).constant(toGarden).with(Constants.foresightConfig.maxPathSpeed.at(0.8));
        slowGarden = line(toGarden,gardenBrake).constant(toGarden).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
        gardenToShoot1 = line(gardenBrake, toShoot1).constant(toShoot1).with(Constants.foresightConfig.maxPathSpeed.at(1.0));
        shoot1ToFlower = line(toShoot1, toFlower).constant(toFlower).with(Constants.foresightConfig.maxPathSpeed.at(1.0));
        flowerLineUp =line(toFlower,lineUpToFlower).constant(toFlower).with(Constants.foresightConfig.maxPathSpeed.at(0.2));
        flowerToShoot2 = line(lineUpToFlower, toShoot2).constant(toShoot2).with(Constants.foresightConfig.maxPathSpeed.at(1.0));
        shootToParkHalf = line(toShoot2, parkHalf).constant(parkHalf);
        parkFully = line(parkHalf, park).linear(parkHalf, park);


        zoneControl = new ZoneControl(robotZone, follower);
        robotBase = new RobotBase(hardwareMap);
        robotBase.tranferBlockersubsystem.setPosition(TransferBlocker.TransferBlockerPosition.STOP);
        robotBase.deployableRampSubsystem.setPosition(DeployableRamp.DeployableRampPosition.AWAY);


        commandGroup = new SequentialCommandGroup(
                //new LaunchCommand(robotBase,800),
                new WaitCommand(3000),
                new TransferCommand(robotBase),
                //new InstantCommand(()->robotBase.intakeSubsystem.setPower(1)),
                new FollowPathCommand(follower, startToGarden),
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(-1)),
                new FollowPathCommand(follower,slowGarden),
                //new WaitCommand(2000),
                // new FollowPathCommand(follower,flowerLineUp),
                new FollowPathCommand(follower, gardenToShoot1),
                new InstantCommand(()->robotBase.intakeSubsystem.setPower(0)),
               // new WaitUntilCommand(() -> robotBase.cameraSubsystem.IsElevationUp()).withTimeout(1000),
                new WaitCommand(8000),
                //new WaitUntilCommand(()-> robotBase.cameraSubsystem.IsElevationUp()),
                //new LaunchCommand(robotBase, 800),
                new TransferCommand (robotBase),
                //     new WaitUntilCommand(()-> robotBase.cameraSubsystem.elevation()),
                new FollowPathCommand(follower, shoot1ToFlower),
                new InstantCommand(()-> robotBase.deployableRampSubsystem.deploy()),
                new InstantCommand(()-> robotBase.intakeSubsystem.setPower(-1)),
                new FollowPathCommand(follower, flowerLineUp),
                new WaitCommand(2000),
                new FollowPathCommand(follower, flowerToShoot2),
                new InstantCommand(()-> robotBase.deployableRampSubsystem.undeploy()),
               // new WaitUntilCommand(() -> robotBase.cameraSubsystem.IsElevationUp()).withTimeout(1000),
                //new WaitUntilCommand(()-> robotBase.cameraSubsystem.IsElevationUp()),
                new TransferCommand(robotBase),
                new FollowPathCommand(follower, shootToParkHalf),
                new FollowPathCommand(follower, parkFully)
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
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }

        follower.update();
      //  robotBase.cameraSubsystem.updateCamera();
        CommandScheduler.getInstance().run();
        telemetry.addData("X: ", follower.pose().x());
        telemetry.addData("Y: ", follower.pose().y());
        telemetry.addData("Heading: ", follower.pose().heading());
     //   telemetry.addData("elevation", robotBase.cameraSubsystem.getElevation());
    //    telemetry.addData("April Tag", robotBase.cameraSubsystem.getTagName());
        telemetry.addData("Alliance: ", DataStorage.alliance);
        telemetry.addData("Pollen Counter", robotBase.distanceSensorSubsystem.hasFourPollen());
        telemetry.update();
    }

    @Override
    public void stop() {
        DataStorage.position = follower.pose();
    }
}
