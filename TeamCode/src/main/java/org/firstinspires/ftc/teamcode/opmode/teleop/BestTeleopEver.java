package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.button.Trigger;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@TeleOp(name = "Best Teleop Ever")
public class BestTeleopEver extends OpMode {
    RobotBase robotBase;
    GamepadEx gamepad;

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        gamepad = new GamepadEx(gamepad1);

        new Trigger(()->gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(()->gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                .whenActive(()->robotBase.intakeSubsystem.setPower(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)))
                .whenInactive(()->robotBase.intakeSubsystem.setPower(0));
        new Trigger(()->gamepad.getButton(GamepadKeys.Button.CIRCLE))
                .whenActive(()->robotBase.windmillSubsystem.setPower(1))
                .whenInactive(()->robotBase.windmillSubsystem.setPower(0));
        new Trigger(()->gamepad.getButton(GamepadKeys.Button.CROSS))
                .whenActive(()->robotBase.launcherSubsystem.setPower(1))
                .whenInactive(()->robotBase.launcherSubsystem.setPower(0));

    }

    @Override
    public void loop() {
        gamepad.readButtons();
        robotBase.chassisSubsystem.leftPower(-gamepad.getLeftY());
        robotBase.chassisSubsystem.rightPower(-gamepad.getRightY());
    }
}
