package org.firstinspires.ftc.teamcode.Opmode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.commands.TransferBlockerCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.button.Trigger;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@Configurable
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
    RobotBase robotBase;
    GamepadEx chassisController;
    boolean isFieldCentric = true;

    ElapsedTime timer;

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        chassisController = new GamepadEx(gamepad1);
        timer = new ElapsedTime();
        chassisController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new LaunchCommand(robotBase, 1000)));
        chassisController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new TransferBlockerCommand(robotBase, TransferBlocker.TransferBlockerPosition.RELEASE)));

        new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                .whenActive(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(
                                (chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) + 1) / 2
                        ))
                ))
                .whenInactive(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5))
                ));
    }

    @Override
    public void start(){
        timer.reset();
    }

    @Override
    public void loop(){
        chassisController.readButtons();
        robotBase.chassisSubsystem.drive(chassisController.getLeftX(), chassisController.getLeftY(), chassisController.getRightX());
        telemetry.addLine("Good luck");
        telemetry.update();


        }
    }

