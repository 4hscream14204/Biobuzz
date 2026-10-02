package org.firstinspires.ftc.teamcode.Opmode;

import static org.firstinspires.ftc.teamcode.base.DataStorage.alliance;
import static org.firstinspires.ftc.teamcode.base.DataStorage.blueCellPoseAudience;
import static org.firstinspires.ftc.teamcode.base.DataStorage.blueCellPoseScoring;
import static org.firstinspires.ftc.teamcode.base.DataStorage.currentCellPose;
import static org.firstinspires.ftc.teamcode.base.DataStorage.redCellPoseAudience;
import static org.firstinspires.ftc.teamcode.base.DataStorage.redCellPoseScoring;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.base.BiobuzzEnums;
import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.AutoTurretHeadingCommandGroup;
import org.firstinspires.ftc.teamcode.commands.DynamicVelocity;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.commands.TransferBlockerCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.button.Trigger;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@TeleOp(name = "George Washingtoad")
public class GeorgeWashingtoadTeleOp extends OpMode {
    RobotBase robotBase;
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start;
    GamepadEx chassisController;
    double velocity = 1000;

    PolygonZone robotZone = new PolygonZone(18, 18);


    PolygonZone blueAudienceSide = new PolygonZone(
            new Point(0, 70),
            new Point(0, 140),
            new Point(140, 140),
            new Point(140, 70)
    );

    PolygonZone redAudienceSide = new PolygonZone(
            new Point(0, 0),
            new Point(0, 70),
            new Point(140, 70),
            new Point(140, 0)
    );

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        chassisController = new GamepadEx(gamepad1);
        robotBase = new RobotBase(hardwareMap);
        follower = Constants.create(hardwareMap);
        start = poseFactory.of(8, 110, 0);
        //DataStorage.currentCellPose = DataStorage.redCellPoseAudience;

        new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                .whileActiveContinuous(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(
                                (chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) + 1) / 2))
                ))
                .whenInactive(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5))
                ));

        chassisController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new TransferBlockerCommand(robotBase, TransferBlocker.TransferBlockerPosition.RELEASE)));

        chassisController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new TransferBlockerCommand(robotBase, TransferBlocker.TransferBlockerPosition.STOP)));

        chassisController.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->velocity += 100)));

        chassisController.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->velocity -= 100)));

        chassisController.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->velocity -= 20)));

        chassisController.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->velocity += 20)));

        new Trigger(()->robotZone.isFullyInside(redAudienceSide) && alliance == BiobuzzEnums.Alliance.RED)
                .whenActive(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->DataStorage.currentCellPose = redCellPoseAudience)))
                .whenInactive(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->DataStorage.currentCellPose = redCellPoseScoring)));

        new Trigger(()->robotZone.isFullyInside(blueAudienceSide) && alliance == BiobuzzEnums.Alliance.BLUE)
                .whenActive(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->DataStorage.currentCellPose = blueCellPoseScoring)))
                .whenInactive(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->DataStorage.currentCellPose = blueCellPoseAudience)));

    }

    @Override
    public void start() {
        follower.setPose(start);
        CommandScheduler.getInstance().schedule(new AutoTurretHeadingCommandGroup(robotBase, follower, redCellPoseAudience));
        CommandScheduler.getInstance().schedule(new DynamicVelocity(robotBase, follower));
        //robotBase.launcherSubsystem.launcherMotor.setVelocity(1800);
    }

    @Override
    public void loop() {
        chassisController.readButtons();

        robotZone.setPosition(follower.pose().x(), follower.pose().y());
        robotZone.setRotation(follower.pose().heading());

        DrivePowers powers = ManualDrive.fieldCentric(
                chassisController.getLeftY(),
                -chassisController.getLeftX(),
                -chassisController.getRightX(),
                follower.pose().heading()
        );

        follower.manual(powers);


        follower.update();
        CommandScheduler.getInstance().run();
        telemetry.addData("Launch Velocity", robotBase.launcherSubsystem.getVelocity());
        telemetry.addData("Velocity variable", velocity);
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Target X", DataStorage.currentCellPose.x());
        telemetry.addData("Target Y", DataStorage.currentCellPose.y());
        telemetry.addData("Turret Angle", robotBase.turretSubsystem.getTurretAngle(follower, currentCellPose));
        telemetry.addData("Distance", follower.pose().distance(redCellPoseAudience));
    }
}
