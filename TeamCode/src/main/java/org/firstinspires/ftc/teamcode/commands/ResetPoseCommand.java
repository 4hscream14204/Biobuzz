package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class ResetPoseCommand extends CommandBase {
    Follower follower;
    public ResetPoseCommand(Follower m_follower){
        follower = m_follower;
    }

    @Override
    public void initialize(){
        follower.setPose(new Pose(8, 133, 0));
    }
}
