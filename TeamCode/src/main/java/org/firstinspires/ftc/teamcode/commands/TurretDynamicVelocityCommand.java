package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.subsystems.Hood;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;

public class TurretDynamicVelocityCommand extends CommandBase {
    ZoneControl zoneControl;
    RobotBase robotBase;
    Follower follower;
    Pose targetTurretPose;

    Pose targetLaunchPose;

    public final Pose cellPoseScoring = new Pose(58, 90);
    public final Pose cellPoseAudience = new Pose(58, 52);
    public final Pose launchPose = new Pose(58, 72);

    public TurretDynamicVelocityCommand(ZoneControl m_zoneControl, RobotBase m_robotBase, Follower m_follower){
        zoneControl = m_zoneControl;
        robotBase = m_robotBase;
        follower = m_follower;
    }

    @Override
    public void initialize() {
        if(zoneControl.getCell() == ZoneControl.Cell.REDAUDIENCE){
            targetTurretPose = cellPoseAudience;
            targetLaunchPose = launchPose;
        }
        else{
            targetTurretPose = cellPoseScoring;
            targetLaunchPose = launchPose;
        }
    }

    @Override
    public void execute(){
        if(zoneControl.getCell() == ZoneControl.Cell.REDAUDIENCE){
            targetTurretPose = cellPoseAudience;
            targetLaunchPose = launchPose;
        }
        else{
            targetTurretPose = cellPoseScoring;
            targetLaunchPose = launchPose;
        }

        robotBase.turretSubsystem.setPositionDeg(robotBase.turretSubsystem.getTurretAngle(follower, targetTurretPose));
        //robotBase.launcherSubsystem.setVelocity(1000);
        //robotBase.launcherSubsystem.setLaunchVelocity(follower.pose().distance(targetLaunchPose));
        robotBase.hoodSubsystem.setPosition(Hood.HoodPosition.MIN);
    }
}
