package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.DistanceSensor;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.button.Trigger;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@TeleOp(name = "Six Wheel")
public class SixWheel extends OpMode {

    Chassis chassis;
    GamepadEx gamepadEx;
    Intake intakeSubsystem;

    DistanceSensor distanceSensorSubsystem;
    TransferBlocker tranferBlockersubsystem;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        distanceSensorSubsystem = new DistanceSensor(hardwareMap.get(DigitalChannel.class, "distanceSensor1"), hardwareMap.get(DigitalChannel.class, "distanceSensor2"), hardwareMap.get(DigitalChannel.class, "distanceSensor3"), hardwareMap.digitalChannel.get("distanceSensor4"));
        chassis = new Chassis(hardwareMap.dcMotor.get("frontLeftMotor"), hardwareMap.dcMotor.get("frontRightMotor"), hardwareMap.dcMotor.get("middleLeftMotor"), hardwareMap.dcMotor.get("middleRightMotor"), hardwareMap.dcMotor.get("backLeftMotor"), hardwareMap.dcMotor.get("backRightMotor"));
        intakeSubsystem = new Intake(
                hardwareMap.servo.get("intakeLeft"),
                hardwareMap.servo.get("intakeRight"),
                hardwareMap.dcMotor.get("intake")
        );
        tranferBlockersubsystem = new TransferBlocker(
                hardwareMap.servo.get("Tblocker")
        );
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

        new Trigger(()->distanceSensorSubsystem.hasFourPollen())
                .whenActive(()->gamepadEx.gamepad.rumble(1000));

        new Trigger(() -> gamepadEx.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(() -> gamepadEx.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                .whileActiveContinuous(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> intakeSubsystem.setPower(
                                (gamepadEx.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepadEx.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) + 1) / 2))
                ))
                .whenInactive(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> intakeSubsystem.setPower(0.5))
                ));

    }

    @Override
    public void start() {
        tranferBlockersubsystem.setPosition(TransferBlocker.TransferBlockerPosition.STOP);
    }

    @Override
    public void loop() {
        gamepadEx.readButtons();
        chassis.drive(gamepadEx.getLeftX(), gamepadEx.getLeftY(), gamepadEx.getRightX(), false);
        telemetry.addData("Has Four Pollen", distanceSensorSubsystem.hasFourPollen());
        CommandScheduler.getInstance().run();
    }
}
