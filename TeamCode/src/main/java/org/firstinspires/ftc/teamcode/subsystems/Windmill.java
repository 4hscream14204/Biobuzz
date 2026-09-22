package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Windmill {
    Servo windmillServo;
    public Windmill(Servo m_windmillMotor){
        windmillServo = m_windmillMotor;
    }
    public void setPower(double power){
        windmillServo.setPosition(power);
    }
}