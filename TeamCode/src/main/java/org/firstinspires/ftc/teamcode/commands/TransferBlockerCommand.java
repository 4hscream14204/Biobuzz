package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class TransferBlockerCommand extends CommandBase {
    RobotBase robotBase;
    TransferBlocker.TransferBlockerPosition position;

    public TransferBlockerCommand(RobotBase m_robotBase, TransferBlocker.TransferBlockerPosition m_position){
        robotBase = m_robotBase;
        position = m_position;
    }

    @Override
    public void initialize(){
        robotBase.tranferBlockersubsystem.setPosition(position.value);
    }
}
