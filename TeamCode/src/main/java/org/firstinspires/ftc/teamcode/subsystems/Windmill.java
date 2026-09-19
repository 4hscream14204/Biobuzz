package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Windmill {
    DcMotor windmillMotor;
    public Windmill(DcMotor m_windmillMotor){
        windmillMotor = m_windmillMotor;
    }
    public void setPower(double power){
        windmillMotor.setPower(power);
    }
}