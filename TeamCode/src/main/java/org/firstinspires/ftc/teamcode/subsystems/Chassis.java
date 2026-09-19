package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Chassis {
    DcMotor leftDrive;
    DcMotor rightDrive;

    public Chassis(DcMotor m_leftDrive, DcMotor m_rightDrive) {
        leftDrive = m_leftDrive;
        rightDrive = m_rightDrive;
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }
}
