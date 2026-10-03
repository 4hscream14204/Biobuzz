package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;

public class FlowerIntakeCommand extends SequentialCommandGroup {
    RobotBase robotBase;
    Follower follower;

    public FlowerIntakeCommand(ZoneControl m_zoneControl, RobotBase m_robotBase, Follower m_follower) {
        m_robotBase = robotBase;
        m_follower = follower;

    }

    @Override
    public void execute() {
        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1));
        new WaitCommand(5000);
        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0));
    }
}
