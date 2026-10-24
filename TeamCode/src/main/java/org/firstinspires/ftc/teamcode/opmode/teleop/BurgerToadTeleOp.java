package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.commands.TransferBlockerCommand;
import org.firstinspires.ftc.teamcode.commands.TransferCommand;
import org.firstinspires.ftc.teamcode.commands.TurretDynamicVelocityCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.DeployableRamp;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.button.Trigger;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@Configurable
@TeleOp(name = "Burger Toad")
public class BurgerToadTeleOp extends OpMode {
    RobotBase robotBase;
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start;
    GamepadEx chassisController;
    double velocity = 0;
    ZoneControl zoneControl;
    public static double intakePower = 0;
    PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;

    PolygonZone robotZone = new PolygonZone(18, 18);

    public final Pose redCellPoseAudience = new Pose(64, 60);

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        chassisController = new GamepadEx(gamepad1);
        robotBase = new RobotBase(hardwareMap);
        follower = Constants.create(hardwareMap);
        start = poseFactory.of(8, 110, 0);
        zoneControl = new ZoneControl(robotZone, follower);
        //DataStorage.currentCellPose = DataStorage.redCellPoseAudience;

        new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                .whileActiveContinuous(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(
                                (chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) - chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER))))))
                .whenInactive(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0))
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

        chassisController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.deployableRampSubsystem.toggleRamp())));

        chassisController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new TransferCommand(robotBase)));

        new Trigger(()->robotBase.distanceSensorSubsystem.hasFourPollen())
                .whenActive(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->chassisController.gamepad.rumble(1000))));



        follower.setPose(start);

    }

    @Override
    public void init_loop() {
        follower.update();
        zoneControl.updateRobotZone();
    }

    @Override
    public void start() {
        follower.setPose(start);
        follower.update();
        zoneControl.updateRobotZone();
        robotBase.deployableRampSubsystem.deployableRampServo1.setPosition(DeployableRamp.DeployableRampPosition.AWAY.value);
        CommandScheduler.getInstance().schedule(new TurretDynamicVelocityCommand(zoneControl, robotBase, follower));
        //robotBase.launcherSubsystem.launcherMotor.setVelocity(1800);
    }

    @Override
    public void loop() {
        chassisController.readButtons();

        zoneControl.updateRobotZone();

        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                follower.pose().heading()
        );
        ManualDrive.driveOrHold(follower, powers);

        //robotBase.launcherSubsystem.setVelocity(velocity);
        //robotBase.intakeSubsystem.setPower(intakePower);


        follower.update();
        CommandScheduler.getInstance().run();
        panelsTelemetry.getTelemetry().addData("Velocity", robotBase.launcherSubsystem.getVelocity());
        telemetry.addData("Launch Velocity", robotBase.launcherSubsystem.getVelocity());
        telemetry.addData("Power", robotBase.launcherSubsystem.launcherMotor.getPower());
        telemetry.addData("Voltage", robotBase.controlHubVoltageSensor.getVoltage());
        telemetry.addData("Velocity variable", velocity);
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Cell", zoneControl.getCell());
        telemetry.addLine(":)");
        panelsTelemetry.getTelemetry().update();
    }
}
