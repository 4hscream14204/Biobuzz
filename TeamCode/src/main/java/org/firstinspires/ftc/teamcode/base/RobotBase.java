package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Windmill;

public class RobotBase {
    public Chassis chassisSubsystem;
    public Intake intakeSubsystem;
    public Windmill windmillSubsystem;
    public Launcher launcherSubsystem;
    public RobotBase(HardwareMap hwMap) {
        chassisSubsystem = new Chassis(
                hwMap.dcMotor.get("leftFront"),
                hwMap.dcMotor.get("rightFront"),
                hwMap.dcMotor.get("leftBack"),
                hwMap.dcMotor.get("rightBack")
        );
        intakeSubsystem = new Intake(
                hwMap.dcMotor.get("intakeMotor"),
                hwMap.servo.get("leftIntakeServo"),
                hwMap.servo.get("rightIntakeServo")
        );
        windmillSubsystem = new Windmill(hwMap.servo.get("windmillServo"));
        launcherSubsystem = new Launcher(hwMap.get(DcMotorEx.class, "launcherMotor"));
    }
}
