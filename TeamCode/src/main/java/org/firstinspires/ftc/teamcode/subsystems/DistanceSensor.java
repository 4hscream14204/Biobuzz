package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;

public class DistanceSensor {
    DigitalChannel digitalChannel;
    DigitalChannel digitalChannel2;
    DigitalChannel digitalChannel3;

    int pollenCounter = 0;

    public DistanceSensor(DigitalChannel m_digitalChannel, DigitalChannel m_digitalChannel2, DigitalChannel m_digitalChannel3) {
        digitalChannel = m_digitalChannel;
        digitalChannel2 = m_digitalChannel2;
        digitalChannel3 = m_digitalChannel3;
    }

    public boolean getTopSensorState() {
        return digitalChannel.getState();
    }

    public boolean getTopMiddleSensorState(){
        return digitalChannel2.getState();
    }

    public boolean getBottomSensorState(){
        return digitalChannel3.getState();
    }

    public void update(){
        if (getTopSensorState() && getTopMiddleSensorState() && getBottomSensorState()) {
            pollenCounter = (pollenCounter + 1) % 4;
        }
    }

    public boolean hasFourPollen(){
        return pollenCounter == 4;
    }
}
