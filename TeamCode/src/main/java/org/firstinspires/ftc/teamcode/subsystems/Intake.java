package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class  Intake {
    Servo intakeServo;
    Servo intakeServo2;
    DcMotor intakeDcMotor;


    public Intake(Servo m_intakeServo, Servo m_intakeServo2, DcMotor m_intakeDcMotor){
        intakeServo = m_intakeServo;
        intakeServo2 = m_intakeServo2;
        intakeDcMotor = m_intakeDcMotor;
    }

    public void setServoPower(double power){
        intakeServo.setPosition(1-power);
        intakeServo2.setPosition(power);
    }

    public void setMotorPower(double Mpower) {
        intakeDcMotor.setPower((Mpower-0.5)*2);
    }

    public void setPower(double power){
        setServoPower(power);
        setMotorPower(power);
    }
}
