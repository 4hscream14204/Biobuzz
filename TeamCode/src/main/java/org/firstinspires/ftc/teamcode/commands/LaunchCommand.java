package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class LaunchCommand extends CommandBase {
    RobotBase robotBase;
    double velocity;

    public LaunchCommand(RobotBase m_robotBase, double m_velocity){
        robotBase = m_robotBase;
        velocity = m_velocity;
    }

    @Override
    public void execute(){robotBase.launcherSubsystem.setVelocity(velocity);}
}
