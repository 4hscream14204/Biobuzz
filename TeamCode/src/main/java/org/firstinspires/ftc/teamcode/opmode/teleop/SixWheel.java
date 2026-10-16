package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@TeleOp(name = "Six Wheel")
public class SixWheel extends OpMode {

    Chassis chassis;
    GamepadEx gamepadEx;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        chassis = new Chassis(hardwareMap.dcMotor.get("frontLeftMotor"), hardwareMap.dcMotor.get("frontRightMotor"), hardwareMap.dcMotor.get("middleLeftMotor"), hardwareMap.dcMotor.get("middleRightMotor"), hardwareMap.dcMotor.get("backLeftMotor"), hardwareMap.dcMotor.get("backRightMotor"));
        gamepadEx = new GamepadEx(gamepad1);

        gamepadEx.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->chassis.frontLeftMotor.setPower(0.5))));

        gamepadEx.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->chassis.frontRightMotor.setPower(0.5))));

        gamepadEx.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->chassis.backLeftMotor.setPower(0.5))));

        gamepadEx.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->chassis.backRightMotor.setPower(0.5))));

        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()-> chassis.drive(0, 0, 0, false))));
    }

    @Override
    public void loop() {
        gamepadEx.readButtons();
        chassis.drive(gamepadEx.getLeftX(), gamepadEx.getLeftY(), gamepadEx.getRightX(), false);
        CommandScheduler.getInstance().run();
    }
}
