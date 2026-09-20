package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;

public class TransferCommand extends SequentialCommandGroup {
    RobotBase robotBase;
    Follower follower;
    public TransferCommand(RobotBase m_robotBase, Follower m_follower) {
        robotBase = m_robotBase;
        follower = m_follower;

        addCommands(
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new LaunchCommand(robotBase, 1000),
                new InstantCommand(() -> robotBase.tranferBlockersubsystem.setPosition(TransferBlocker.TransferBlockerPosition.RELEASE))
        );


    }

}
