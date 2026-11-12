package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class DeployableRamp {
    public Servo deployableRampServo1;

    public boolean isDeployed;

    public enum DeployableRampPosition{
        AWAY(0.5944),
        DEPLOYED(0.1944);
        public final double value;

        DeployableRampPosition(double pos) {this.value = pos; }
    }
    public DeployableRamp(Servo m_deployableRamp1) {
        deployableRampServo1 = m_deployableRamp1;
        isDeployed = false;
    }

    public void setPosition(DeployableRampPosition position){
        deployableRampServo1.setPosition(position.value);
    }

    public void toggleRamp(){
        if(isDeployed){
            deployableRampServo1.setPosition(DeployableRampPosition.AWAY.value);
            isDeployed = false;
        }
        else{
            deployableRampServo1.setPosition(DeployableRampPosition.DEPLOYED.value);
            isDeployed = true;
        }
    }

    public void deploy(){
        if (!isDeployed) {
            deployableRampServo1.setPosition(DeployableRampPosition.DEPLOYED.value);
            isDeployed = true;
        }
    }

    public void undeploy(){
        if (isDeployed) {
            deployableRampServo1.setPosition(DeployableRampPosition.AWAY.value);
            isDeployed = false;
        }
    }
}
