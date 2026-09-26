package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class DynamicVelocity extends CommandBase {
    RobotBase robotBase;
    Follower follower;
    Pose redCellPoseScoring = new Pose(64, 85);

    public DynamicVelocity(RobotBase m_robotBase, Follower m_follower){
        robotBase = m_robotBase;
        follower = m_follower;
    }

    @Override
    public void execute(){
        robotBase.launcherSubsystem.setLaunchVelocity(follower.pose().distance(redCellPoseScoring));
    }
}
