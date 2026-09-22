package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.opencv.features2d.BRISK;

import java.text.BreakIterator;

public class Chassis {
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;

    public Chassis(DcMotor m_leftFront, DcMotor m_rightFront, DcMotor m_leftBack, DcMotor m_rightBack) {
        leftFront = m_leftFront;
        rightFront = m_rightFront;
        leftBack = m_leftBack;
        rightBack = m_rightBack;

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void drive(double m_leftStickX, double m_leftStickY, double m_rightStickX) {
        double denominator;

        double frontLeftPower;
        double backLeftPower;
        double frontRightPower;
        double backRightPower;


        //Field Centric
        /*double heading = follower.pose().heading();
        double rotX = m_leftStickX * Math.cos(-heading) - m_leftStickY * Math.sin(-heading);
        double rotY = m_leftStickX * Math.sin(-heading) - m_leftStickY * Math.cos(-heading);
        double rotationPower = m_rightStickX * Math.abs(m_rightStickX);


        denominator = Math.max(Math.abs(rotX) + Math.abs(rotY) + Math.abs(rotationPower), 1);

        frontLeftPower = (rotY + rotX + rotationPower) / denominator;
        backLeftPower = (rotY - rotX + rotationPower) / denominator;
        frontRightPower = (rotY + rotX - rotationPower) / denominator;
        backRightPower = (rotY - rotX - rotationPower) / denominator;*/

        //Robot Centric
        double y = -m_leftStickY;
        double x = m_leftStickX * 1.1;
        double rx = -m_rightStickX;

        denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeftPower = (y + x + rx) / denominator;
        backLeftPower = (y - x + rx) / denominator;
        frontRightPower = (y + x - rx) / denominator;
        backRightPower = (y - x - rx) / denominator;

        leftFront.setPower(frontLeftPower);
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);
    }
}
