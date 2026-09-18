package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Chassis {
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;

    double dblFrontLeftPower;
    double dblFrontRightPower;
    double dblBackLeftPower;
    double dblBackRightPower;
    public boolean bolFieldCentric;

    GoBildaPinpointDriver pinpointDriver;
    double leftStickX;
    double leftStickY;
    double rotationPower;
    double botHeading;
    double dblDenominator;
    public  Chassis (DcMotor m_frontLeftMotor , DcMotor m_frontRightMotor , DcMotor m_backLeftMotor , DcMotor m_backRightMotor) {
        frontLeftMotor = m_frontLeftMotor;
        frontRightMotor = m_frontRightMotor;
        backLeftMotor = m_backLeftMotor;
        backRightMotor = m_backRightMotor;

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void drive (double m_leftStickX, double m_leftStickY, double m_rightStickX){
        leftStickX = (m_leftStickY * Math.abs(m_leftStickY) * -1);
        leftStickY = m_leftStickX * Math.abs(m_leftStickX);
        rotationPower = m_rightStickX * Math.abs(m_rightStickX);
        botHeading = pinpointDriver.getHeading(AngleUnit.RADIANS);

        if(bolFieldCentric){
            double rotX = leftStickX * Math.cos(-botHeading) - leftStickY * Math.sin(-botHeading);
            double rotY = leftStickX * Math.sin(-botHeading) + leftStickY * Math.cos(-botHeading);

            dblDenominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rotationPower), 1);
            dblFrontLeftPower = (rotY + rotX + rotationPower) / dblDenominator;
            dblBackLeftPower = (rotY - rotX + rotationPower) / dblDenominator;
            dblFrontRightPower = (rotY - rotX - rotationPower) / dblDenominator;
            dblBackRightPower = (rotY + rotX - rotationPower) / dblDenominator;
        }
        else{
            dblDenominator = Math.max(Math.abs(leftStickX) + Math.abs(leftStickX) + Math.abs(rotationPower), 1);
            dblFrontLeftPower = (leftStickY + leftStickX + rotationPower) / dblDenominator;
            dblBackLeftPower = (leftStickY - leftStickX + rotationPower) / dblDenominator;
            dblFrontRightPower = (leftStickY - leftStickX - rotationPower) / dblDenominator;
            dblBackRightPower = (leftStickY + leftStickX - rotationPower) / dblDenominator;
        }
        frontLeftMotor.setPower(dblFrontLeftPower);
        frontRightMotor.setPower(dblFrontRightPower);
        backLeftMotor.setPower(dblBackLeftPower);
        backRightMotor.setPower(dblBackRightPower);
    }



}
