package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class AutoTurretHeadingCommandGroup extends CommandBase {
    RobotBase robotBase;
    Follower follower;
    Pose currentCellPose;


    public AutoTurretHeadingCommandGroup (RobotBase m_robotBase, Follower m_follower, Pose m_cellPose){
        m_robotBase = robotBase;
        m_follower = follower;
        m_cellPose = currentCellPose;
        currentCellPose = DataStorage.currentCellPose;
    }
    @Override
    public void execute () {
        robotBase.turretSubsystem.setPositionDeg(robotBase.turretSubsystem.getTurretAngle(follower, currentCellPose));
    }

}
