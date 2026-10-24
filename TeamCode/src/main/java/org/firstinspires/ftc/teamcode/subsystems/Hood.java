package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Hood {
    Servo hoodServo;

    public enum HoodPosition {
        MAX(0.0833),
        MIN(1);
        //Numbers go here
        private final double value;
        HoodPosition(double m_value) {
            value = m_value;
        }
    }

    public Hood(Servo m_hoodServo) {
        hoodServo = m_hoodServo;
    }

    public void setPosition(HoodPosition hoodPosition) {
        hoodServo.setPosition(hoodPosition.value);
    }

    public void setPosition(double position){
        hoodServo.setPosition(position);
    }

    public void autoSetPosition(double m_distance){
        double distance = (-0.0234 * m_distance) + 1.6857;
        if(distance > HoodPosition.MIN.value){
            hoodServo.setPosition(distance);
        }
        else if(distance > HoodPosition.MAX.value){
            hoodServo.setPosition(HoodPosition.MAX.value);
        }
        else if(distance < HoodPosition.MIN.value){
            hoodServo.setPosition(HoodPosition.MIN.value);
        }
    }
}
