package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
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
        robotBase = new RobotBase(hardwareMap);
        gamepad = new GamepadEx(gamepad1);

        //Intake
        new Trigger(()->gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(()->gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                .whenActive(()->robotBase.intakeSubsystem.setPower(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)))
                .whenInactive(()->robotBase.intakeSubsystem.setPower(0));

        //Launch
        new Trigger(()->gamepad.getButton(GamepadKeys.Button.CIRCLE))
                .whenActive(new LaunchCommand(robotBase, velocity));

        //Tune launcher
        new Trigger(()->gamepad.getButton(GamepadKeys.Button.DPAD_UP))
                .whileActiveOnce(new InstantCommand(()->velocity += 5));
        new Trigger(()->gamepad.getButton(GamepadKeys.Button.DPAD_DOWN))
                .whileActiveOnce(new InstantCommand(()->velocity -= 5));
    }

    @Override
    public void loop() {
        gamepad.readButtons();
        robotBase.chassisSubsystem.leftPower(-gamepad.getLeftY());
        robotBase.chassisSubsystem.rightPower(-gamepad.getRightY());
        telemetry.addData("Current launch velocity: ", velocity);
    }
}
