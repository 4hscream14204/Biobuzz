package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Launcher {
    DcMotorEx launcherMotor;
    public Launcher(DcMotorEx m_launcherMotor){
        launcherMotor = m_launcherMotor;
    }
    public void setVelocity(double power){
       launcherMotor.setVelocity(power);
    }
}
