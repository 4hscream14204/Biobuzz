package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class DeployableRamp {
    public Servo deployableRampServo1;
    public Servo deployableRampServo2;

    public boolean stopped;

    public enum deployableRampPosition{
        AIR(0),
        DEPLOYED(0);
        public final double value;

        deployableRampPosition(double pos) {this.value = pos; }
    }
    public DeployableRamp(Servo m_deployableRamp1, Servo m_deployableRamp2) {
        deployableRampServo1 = m_deployableRamp1;
        deployableRampServo2 = m_deployableRamp2;
        stopped = true;
    }
}
