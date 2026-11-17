package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;

@TeleOp(name = "Motor Test")
public class MotorTest extends OpMode {
    RobotBase robotBase;
    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.aWasPressed()){
            robotBase.chassisSubsystem.frontLeftMotor.setPower(0.5);
        }
        if(gamepad1.bWasPressed()){
            robotBase.chassisSubsystem.frontRightMotor.setPower(0.5);
        }
        if(gamepad1.xWasPressed()){
            robotBase.chassisSubsystem.backLeftMotor.setPower(0.5);
        }
        if(gamepad1.yWasPressed()){
            robotBase.chassisSubsystem.backRightMotor.setPower(0.5);
        }
        if(gamepad1.rightBumperWasPressed()){
            robotBase.chassisSubsystem.frontLeftMotor.setPower(0);
            robotBase.chassisSubsystem.frontRightMotor.setPower(0);
            robotBase.chassisSubsystem.backLeftMotor.setPower(0);
            robotBase.chassisSubsystem.backRightMotor.setPower(0);
        }
    }
}
