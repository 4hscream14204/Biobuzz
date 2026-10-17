package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class DeployableRamp {
    public Servo deployableRampServo;
    public boolean stopped;

    public enum deployableRampPosition{
        AIR(0),
        DEPLOYED(0);
        public final double value;

        deployableRampPosition(double pos) {this.value = pos; }
    }
    public DeployableRamp(Servo m_deployableRamp) {
        deployableRampServo = m_deployableRamp;
        stopped = true;
    }
}
