package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class  Intake {
    DcMotor intakeDcMotor;


    public Intake(DcMotor m_intakeDcMotor){
        intakeDcMotor = m_intakeDcMotor;
    }

    public void setPower(double power){
        intakeDcMotor.setPower(power);
    }
}
