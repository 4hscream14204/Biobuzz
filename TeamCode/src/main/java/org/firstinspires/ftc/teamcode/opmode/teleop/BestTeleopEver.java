package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.button.Trigger;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@TeleOp(name = "Best Teleop Ever")
public class BestTeleopEver extends OpMode {
    RobotBase robotBase;
    GamepadEx gamepad;
    int velocity = 1000;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        robotBase = new RobotBase(hardwareMap);
        gamepad = new GamepadEx(gamepad1);

        //Intake
        new Trigger(() -> gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(() -> gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                .whileActiveContinuous(() -> robotBase.intakeSubsystem.setPower(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)))
                .whenInactive(() -> robotBase.intakeSubsystem.setPower(0));

        //Launch
        /*gamepad.getGamepadButton(GamepadKeys.Button.CROSS)
                .whenActive(() -> CommandScheduler.getInstance().schedule(new LaunchCommand(robotBase, velocity)));*/
        gamepad.getGamepadButton(GamepadKeys.Button.CROSS)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new InstantCommand(() -> robotBase.windmillSubsystem.setPower(1))))
                .whenReleased(() -> CommandScheduler.getInstance().schedule(new InstantCommand(() -> robotBase.windmillSubsystem.setPower(0.5))));

        //Tune launcher
        gamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(() -> robotBase.launcherSubsystem.setVelocity(robotBase.launcherSubsystem.getVelocity() + 100));
        gamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> robotBase.launcherSubsystem.setVelocity(robotBase.launcherSubsystem.getVelocity() - 100));
        gamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(() -> robotBase.launcherSubsystem.setVelocity(velocity));
    }

    @Override
    public void start() {
        robotBase.launcherSubsystem.setVelocity(velocity);
    }

    @Override
    public void loop() {
        gamepad.readButtons();
        robotBase.chassisSubsystem.drive(gamepad.getLeftX(), gamepad.getLeftY(), gamepad.getRightX());
        CommandScheduler.getInstance().run();
        telemetry.addData("Current launch velocity: ", robotBase.launcherSubsystem.getVelocity());
    }
}
