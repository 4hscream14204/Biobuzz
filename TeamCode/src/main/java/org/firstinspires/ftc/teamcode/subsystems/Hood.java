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
}
