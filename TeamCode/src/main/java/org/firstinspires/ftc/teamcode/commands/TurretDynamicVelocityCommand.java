package org.firstinspires.ftc.teamcode.commands;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.subsystems.Hood;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;

@Configurable
public class TurretDynamicVelocityCommand extends CommandBase {
    ZoneControl zoneControl;
    RobotBase robotBase;
    Follower follower;
    Pose targetTurretPose;

    Pose targetLaunchPose;

    double velocity;

    public static Pose cellPoseScoringLeft = ZoneControl.CellPosition.SCORINGLEFT.pose;
    public static Pose cellPoseScoringRight = ZoneControl.CellPosition.SCORINGRIGHT.pose;
    public static Pose cellPoseAudienceLeft = ZoneControl.CellPosition.AUDIENCELEFT.pose;
    public static Pose cellPoseAudienceRight = ZoneControl.CellPosition.AUDIENCERIGHT.pose;
    public static final Pose launchPose = new Pose(56, 70);

    public TurretDynamicVelocityCommand(ZoneControl m_zoneControl, RobotBase m_robotBase, Follower m_follower){
        zoneControl = m_zoneControl;
        robotBase = m_robotBase;
        follower = m_follower;
    }

    public TurretDynamicVelocityCommand(ZoneControl m_zoneControl, RobotBase m_robotBase, Follower m_follower, double velocity){
        zoneControl = m_zoneControl;
        robotBase = m_robotBase;
        follower = m_follower;
        this.velocity = velocity;
    }

    @Override
    public void initialize() {
        if(zoneControl.getCell() == ZoneControl.Cell.REDAUDIENCELEFT){
            targetTurretPose = cellPoseAudienceLeft;
        }
        else if(zoneControl.getCell() == ZoneControl.Cell.REDAUDIENCERIGHT){
            targetTurretPose = cellPoseAudienceRight;
        }
        else if(zoneControl.getCell() == ZoneControl.Cell.REDSCORINGLEFT){
            targetTurretPose = cellPoseScoringLeft;
        }
        else{
            targetTurretPose = cellPoseScoringRight;
        }
    }

    @Override
    public void execute(){
        if(zoneControl.getCell() == ZoneControl.Cell.REDAUDIENCELEFT){
            targetTurretPose = cellPoseAudienceLeft;
        }
        else if(zoneControl.getCell() == ZoneControl.Cell.REDAUDIENCERIGHT){
            targetTurretPose = cellPoseAudienceRight;
        }
        else if(zoneControl.getCell() == ZoneControl.Cell.REDSCORINGLEFT){
            targetTurretPose = cellPoseScoringLeft;
        }
        else{
            targetTurretPose = cellPoseScoringRight;
        }

        robotBase.turretSubsystem.setPositionDeg(robotBase.turretSubsystem.getTurretAngle(follower, targetTurretPose));
        //robotBase.launcherSubsystem.setVelocity(velocity);
        //robotBase.hoodSubsystem.setPosition(0.1889);
        robotBase.launcherSubsystem.setLaunchVelocity(follower.pose().distance(launchPose));
        robotBase.hoodSubsystem.autoSetPosition(follower.pose().distance(launchPose));
    }
}