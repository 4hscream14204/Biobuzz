package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class TransferBlocker {
    public Servo transferBlockerServo;
    public boolean stopped;

    public enum TransferBlockerPosition{
        STOP(0),
        RELEASE(0);
        public final double value;

        TransferBlockerPosition(double pos) {this.value = pos; }
    }
    public TransferBlocker(Servo m_transferBlocker) {
        transferBlockerServo = m_transferBlocker;
        stopped = true;
    }
    public void setPosition(double position) {
        transferBlockerServo.setPosition(position);
    }
    public void setPosition(TransferBlockerPosition transferBlockerPosition){
        transferBlockerServo.setPosition(transferBlockerPosition.value);
        if (transferBlockerPosition == TransferBlockerPosition.STOP){ stopped = true;}
        else {stopped = false;}
    }
}
