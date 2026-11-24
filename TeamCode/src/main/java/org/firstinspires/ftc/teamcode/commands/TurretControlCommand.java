package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.skeletonarmy.marrow.zones.Zone;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class TurretControlCommand extends CommandBase {
    ZoneControl zoneControl;
    RobotBase robotBase;
    Follower follower;
    Pose targetPose;

    public final Pose redCellPoseScoring = ZoneControl.CellPosition.REDSCORING.pose;
    public final Pose redCellPoseAudience = ZoneControl.CellPosition.REDAUDIENCE.pose;
    public final Pose blueCellPoseScoring = ZoneControl.CellPosition.BLUESCORING.pose;
    public final Pose blueCellPoseAudience = ZoneControl.CellPosition.BLUEAUDIENCE.pose;
    public TurretControlCommand(ZoneControl m_zoneControl, RobotBase m_robotBase, Follower m_follower){
        zoneControl = m_zoneControl;
        robotBase = m_robotBase;
        follower = m_follower;
    }

    @Override
    public void execute(){
        if(zoneControl.getCell() == ZoneControl.Cell.REDAUDIENCELEFT){
            targetPose = blueCellPoseAudience;
        }
        else if(zoneControl.getCell() == ZoneControl.Cell.REDSCORINGLEFT){
            targetPose = blueCellPoseScoring;
        }
        else if(zoneControl.getCell() == ZoneControl.Cell.REDSCORINGRIGHT){
            targetPose = redCellPoseAudience;
        }
        else{
            targetPose = redCellPoseScoring;
        }

        robotBase.turretSubsystem.setPositionDeg(robotBase.turretSubsystem.getTurretAngle(follower, targetPose));
    }
}