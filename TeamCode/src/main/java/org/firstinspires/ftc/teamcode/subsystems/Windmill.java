package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Windmill {
    Servo windmillMotor;
    public Windmill(Servo m_windmillMotor){
        windmillMotor = m_windmillMotor;
    }
    public void setPower(double power){
        windmillMotor.setPosition(power);
    }
}