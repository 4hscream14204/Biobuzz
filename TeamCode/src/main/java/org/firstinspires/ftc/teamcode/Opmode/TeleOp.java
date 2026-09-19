package org.firstinspires.ftc.teamcode.Opmode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@Configurable
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
    RobotBase robotBase;
    GamepadEx chassisController;
    boolean isFieldCentric = true;
    TelemetryManager telemetryM;

    ElapsedTime timer;

    @Override
    public void init(){
        robotBase = new RobotBase(hardwareMap);
        chassisController = new GamepadEx(gamepad1);
        timer = new ElapsedTime();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }
    @Override
    public void start(){
        timer.reset();
    }
    @Override
    public void loop(){
        chassisController.readButtons();
        telemetry.addLine("Good luck");
        telemetryM.update();


        }
    }

