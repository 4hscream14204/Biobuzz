package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;

public class DistanceSensor {
    DigitalChannel digitalChannel;
    DigitalChannel digitalChannel2;
    DigitalChannel digitalChannel3;
    DigitalChannel digitalChannel4;

    public DistanceSensor(DigitalChannel m_digitalChannel, DigitalChannel m_digitalChannel2, DigitalChannel m_digitalChannel3, DigitalChannel m_digitalChannel4) {
        digitalChannel = m_digitalChannel;
        digitalChannel2 = m_digitalChannel2;
        digitalChannel3 = m_digitalChannel3;
        digitalChannel4 = m_digitalChannel4;
    }

    public boolean getTopSensorState() {
        return digitalChannel.getState();
    }

    public boolean getTopMiddleSensorState(){
        return digitalChannel2.getState();
    }

    public boolean getBottomMiddleSensorState(){
        return digitalChannel3.getState();
    }

    public boolean getBottomSensorState(){
        return digitalChannel4.getState();
    }

    public boolean hasFourPollen(){
        return getTopSensorState() && getTopMiddleSensorState() && getBottomMiddleSensorState() && getBottomSensorState();
    }
}
