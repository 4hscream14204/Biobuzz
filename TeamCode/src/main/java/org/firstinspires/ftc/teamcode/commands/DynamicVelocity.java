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
    Pose targetPose;

    public DynamicVelocity(RobotBase m_robotBase, Follower m_follower, Pose m_targetPose){
        robotBase = m_robotBase;
        follower = m_follower;
        targetPose = m_targetPose;
    }

    @Override
    public void execute(){
        //robotBase.launcherSubsystem.setVelocity(1000);
        robotBase.launcherSubsystem.setLaunchVelocity(follower.pose().distance(targetPose));
    }
}
