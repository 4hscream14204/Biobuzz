package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;

public class DistanceSensor {
    DigitalChannel digitalChannel;

    public DistanceSensor(DigitalChannel m_digitalChannel) {
        digitalChannel = m_digitalChannel;
    }

    public boolean getState() {
        return digitalChannel.getState();
    }
}
