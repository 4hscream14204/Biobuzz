package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

public class FlowerIntakeCommand extends SequentialCommandGroup {
    RobotBase robotBase;

    public FlowerIntakeCommand(RobotBase m_robotBase) {
        robotBase = m_robotBase;
    }

    @Override
    public void execute() {
        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1));
        new WaitCommand(5000);
        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0));
    }
}
