package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@TeleOp(name = "Motor Test")
public class SixWheelMotorTest extends OpMode {
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor middleLeftMotor;
    DcMotor middleRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    GamepadEx gamepad;
    @Override
    public void init() {
        gamepad = new GamepadEx(gamepad1);
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        middleLeftMotor = hardwareMap.dcMotor.get("middleLeftMotor");
        middleRightMotor = hardwareMap.dcMotor.get("middleRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        middleLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        middleRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        middleLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        middleRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {

        gamepad.readButtons();

        if(gamepad.wasJustPressed(GamepadKeys.Button.A)){
            frontLeftMotor.setPower(0.5);
        }
        if(gamepad.wasJustPressed(GamepadKeys.Button.B)){
            frontRightMotor.setPower(0.5);
        }
        if(gamepad.wasJustPressed(GamepadKeys.Button.X)){
            backLeftMotor.setPower(0.5);
        }
        if(gamepad.wasJustPressed(GamepadKeys.Button.Y)){
            backRightMotor.setPower(0.5);
        }
        if(gamepad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
            middleLeftMotor.setPower(0.5);
        }
        if(gamepad.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
            middleRightMotor.setPower(0.5);
        }
        if(gamepad.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            middleLeftMotor.setPower(0);
            middleRightMotor.setPower(0);
        }

        telemetry.addLine("A: FL");
        telemetry.addLine("B: FR");
        telemetry.addLine("X: BL");
        telemetry.addLine("Y: BR");
        telemetry.addLine("LB: ML");
        telemetry.addLine("DPAD-DOWN: MR");
        telemetry.addLine("DPAD-UP: All off");
    }
}
