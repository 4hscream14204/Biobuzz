package org.firstinspires.ftc.teamcode.Opmode;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
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
    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        chassisController = new GamepadEx(gamepad1);
        robotBase = new RobotBase(hardwareMap);
        //follower = Constants.create(hardwareMap);
        //start = poseFactory.of(40, 8, 0);
        //DataStorage.currentCellPose = DataStorage.redCellPoseAudience;

        new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                .whenActive(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.intakeSubsystem.setMotorPower(
                                (chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) + 1) / 2
                        ))
                ))
                .whenInactive(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.intakeSubsystem.setMotorPower(0.5))
                ));

        chassisController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new TransferBlockerCommand(robotBase, TransferBlocker.TransferBlockerPosition.RELEASE)));

        chassisController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new TransferBlockerCommand(robotBase, TransferBlocker.TransferBlockerPosition.STOP)));
    }

    @Override
    public void start() {
        //follower.setPose(start);
        robotBase.turretSubsystem.setPosition(0.5);
        robotBase.launcherSubsystem.launcherMotor.setVelocity(1800);
    }

    @Override
    public void loop() {
        chassisController.readButtons();
        robotBase.chassisSubsystem.drive(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
        //follower.update();
        CommandScheduler.getInstance().run();
        telemetry.addData("Launch Velocity", robotBase.launcherSubsystem.getVelocity());
        telemetry.addData("Velocity variable", velocity);
    }
}
