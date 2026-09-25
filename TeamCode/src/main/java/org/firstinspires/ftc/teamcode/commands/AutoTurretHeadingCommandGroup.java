package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.base.DataStorage.currentCellPose;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class AutoTurretHeadingCommandGroup extends CommandBase {
    RobotBase robotBase;
    Follower follower;


    public AutoTurretHeadingCommandGroup (RobotBase m_robotBase, Follower m_follower, Pose m_cellPose){
        robotBase = m_robotBase;
        follower = m_follower;
        currentCellPose = m_cellPose;
    }
    @Override
    public void execute () {
        robotBase.turretSubsystem.setPositionDeg(robotBase.turretSubsystem.getTurretAngle(follower, currentCellPose));
    }

}
