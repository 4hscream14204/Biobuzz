package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class MoveCommand extends CommandBase {
    RobotBase robotBase;

    public MoveCommand(RobotBase m_robotBase) {
        robotBase = m_robotBase;
    }

    @Override
    public void initialize() {
        robotBase.chassisSubsystem.drive(0, 1, 0);
    }
}
