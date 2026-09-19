package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;

public class RobotBase {
    Chassis chassisSubsystem;
    Intake intakeSubsystem;
    //Windmill windmillSubsystem;
    Launcher launcherSubsystem;
    public RobotBase(HardwareMap hwMap) {
        chassisSubsystem = new Chassis(
                hwMap.dcMotor.get("leftMotor"),
                hwMap.dcMotor.get("rightMotor")
        );
        intakeSubsystem = new Intake(
                hwMap.dcMotor.get("intakeMotor"),
                hwMap.servo.get("leftIntakeServo"),
                hwMap.servo.get("rightIntakeServo")
        );
        
    }
}
