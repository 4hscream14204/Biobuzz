package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Hood {
    Servo hoodServo;

    public enum HoodPosition {;
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
        double distance = (-0.0435 * m_distance) + 2.6007;
        hoodServo.setPosition(distance);
    }
}
