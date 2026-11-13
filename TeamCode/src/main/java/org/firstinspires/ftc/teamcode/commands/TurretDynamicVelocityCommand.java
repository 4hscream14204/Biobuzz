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

    public static Pose cellPoseScoring = new Pose(56, 90);
    public static Pose cellPoseAudience = new Pose(56, 52);
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
        if(zoneControl.getCell() == ZoneControl.Cell.REDAUDIENCE){
            targetTurretPose = cellPoseAudience;
        }
        else{
            targetTurretPose = cellPoseScoring;
        }
    }

    @Override
    public void execute(){
        if(zoneControl.getCell() == ZoneControl.Cell.REDAUDIENCE){
            targetTurretPose = cellPoseAudience;
        }
        else{
            targetTurretPose = cellPoseScoring;
        }

        robotBase.turretSubsystem.setPositionDeg(robotBase.turretSubsystem.getTurretAngle(follower, targetTurretPose));
        //robotBase.launcherSubsystem.setVelocity(velocity);
        //robotBase.hoodSubsystem.setPosition(0.1889);
        robotBase.launcherSubsystem.setLaunchVelocity(follower.pose().distance(launchPose));
        robotBase.hoodSubsystem.autoSetPosition(follower.pose().distance(launchPose));
    }
}
