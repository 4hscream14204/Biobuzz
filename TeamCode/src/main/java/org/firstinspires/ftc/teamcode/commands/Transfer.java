package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class Transfer extends CommandBase {
    RobotBase robotBase;

    public Transfer(RobotBase m_robotBase){
        robotBase = m_robotBase;
    }

    @Override
    public void execute(){robotBase.tranferBlockersubsystem.setPosition(robotBase.tranferBlockersubsystem.stopped ? TransferBlocker.TransferBlockerPosition.RELEASE : TransferBlocker.TransferBlockerPosition.STOP);}
}
