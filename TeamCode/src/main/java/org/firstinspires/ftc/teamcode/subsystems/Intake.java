package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    DcMotor intakeMotor;
    Servo leftServo;
    Servo rightServo;
    public Intake(DcMotor m_intakeMotor, Servo m_leftServo, Servo m_rightServo){
        intakeMotor = m_intakeMotor;
        rightServo = m_rightServo;
        leftServo = m_leftServo;
    }
    public void setPower(double power){
        double servoPower = (power + 1)/2;
        intakeMotor.setPower(power);
        rightServo.setPosition(servoPower);
        leftServo.setPosition(1 - servoPower);
    }
}