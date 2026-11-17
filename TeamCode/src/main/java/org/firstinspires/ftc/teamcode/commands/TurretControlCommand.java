package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class TurretControlCommand extends CommandBase {
    ZoneControl zoneControl;
    RobotBase robotBase;
    Follower follower;
    Pose targetPose;

    public final Pose redCellPoseScoring = new Pose(64, 85);
    public final Pose redCellPoseAudience = new Pose(64, 60);
    public final Pose blueCellPoseScoring = new Pose(83.5, 85);
    public final Pose blueCellPoseAudience = new Pose(83.5, 60);
    public TurretControlCommand(ZoneControl m_zoneControl, RobotBase m_robotBase, Follower m_follower){
        zoneControl = m_zoneControl;
        robotBase = m_robotBase;
        follower = m_follower;
    }

    @Override
    public void execute(){
        if(zoneControl.getCell() == ZoneControl.Cell.BLUEAUDIENCE){
            targetPose = blueCellPoseAudience;
        }
        else if(zoneControl.getCell() == ZoneControl.Cell.BLUESCORING){
            targetPose = blueCellPoseScoring;
        }
        else if(zoneControl.getCell() == ZoneControl.Cell.REDAUDIENCE){
            targetPose = redCellPoseAudience;
        }
        else{
            targetPose = redCellPoseScoring;
        }

        robotBase.turretSubsystem.setPositionDeg(robotBase.turretSubsystem.getTurretAngle(follower, targetPose));
    }
}
