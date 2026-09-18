package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Hood;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;

import java.util.List;

public class RobotBase {

    public Chassis chassisSubsystem;
    public Hood hoodSubsystem;
    public Intake intakeSubsystem;
    public Launcher launcherSubsystem;
    public Turret turretSubsystem;
    public TransferBlocker tranferBlockersubsystem;
    public List<VoltageSensor> voltageSensor;
    public VoltageSensor controlHubVoltageSensor;

    public RobotBase(HardwareMap hwMap){
        voltageSensor = hwMap.getAll(VoltageSensor.class);
        controlHubVoltageSensor = voltageSensor.get(0);
        chassisSubsystem = new Chassis(
                hwMap.dcMotor.get("leftFront"),
                hwMap.dcMotor.get("rightFront"),
                hwMap.dcMotor.get("leftRear"),
                hwMap.dcMotor.get("rightRear")
        );
        hoodSubsystem = new Hood(
                hwMap.servo.get("hood")
        );
        intakeSubsystem = new Intake(
                hwMap.servo.get("intakeLeft"),
                hwMap.servo.get("intakeRight"),
                hwMap.dcMotor.get("intake")
        );
        launcherSubsystem = new Launcher(
                hwMap.get(DcMotorEx.class, "launcher1"),
                hwMap.get(DcMotorEx.class, "launcher2"),
                controlHubVoltageSensor
        );
        /*turretSubsystem = new Turret(
                hwMap.servo.get("turret1"),
                hwMap.servo.get("turret2"),
        );*/
        tranferBlockersubsystem = new TransferBlocker(
                hwMap.servo.get("Tblocker")
        );
    }
}
