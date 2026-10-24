package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.ParallelCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

public class TransferCommand extends SequentialCommandGroup {
    RobotBase robotBase;
    public TransferCommand(RobotBase m_robotBase) {
        robotBase = m_robotBase;

        addCommands(
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(-0.5)),
                new InstantCommand(() -> robotBase.tranferBlockersubsystem.setPosition(TransferBlocker.TransferBlockerPosition.RELEASE)),
                new WaitCommand(2000),
                new ParallelCommandGroup(
                        new InstantCommand(()->robotBase.tranferBlockersubsystem.setPosition(TransferBlocker.TransferBlockerPosition.STOP)),
                        new InstantCommand(()->robotBase.intakeSubsystem.setPower(0)))
        );


    }

}
