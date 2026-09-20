package org.firstinspires.ftc.teamcode.commands;

import android.os.Bundle;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;

public class LaunchCommand extends CommandBase {
    RobotBase robotBase;
    double velocity;

    public LaunchCommand(RobotBase m_robotBase, double m_velocity) {
        robotBase = m_robotBase;
        velocity = m_velocity;
    }
    @Override
    public void initialize(){
        robotBase.launcherSubsystem.setVelocity(velocity);
        robotBase.windmillSubsystem.setPower(1);
}
}
