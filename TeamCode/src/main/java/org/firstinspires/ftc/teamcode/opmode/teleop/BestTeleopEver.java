package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.commands.MoveCommand;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.button.Trigger;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@TeleOp(name = "Best Teleop Ever", group = "Fun")
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
                .whenActive(() -> robotBase.intakeSubsystem.setPower(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)))
                .whenInactive(() -> robotBase.intakeSubsystem.setPower(0));

        //Launch
        gamepad.getGamepadButton(GamepadKeys.Button.SQUARE)
                .whenActive(() -> CommandScheduler.getInstance().schedule(new LaunchCommand(robotBase, velocity)));

        //Drive
        gamepad.getGamepadButton(GamepadKeys.Button.CIRCLE)
                        .whenPressed(() -> CommandScheduler.getInstance().schedule(new MoveCommand(robotBase)));

        //Tune launcher
        gamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(() -> velocity += 5);
        gamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> velocity -= 5);
    }

    @Override
    public void loop() {
        gamepad.readButtons();
        robotBase.chassisSubsystem.drive(gamepad.getLeftX(), gamepad.getLeftY(), gamepad.getRightX());
        CommandScheduler.getInstance().run();
        telemetry.addData("Current launch velocity: ", velocity);
    }
}
