package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.subsystems.Limelight;

public class PoseGenerator {
    Limelight limelight;
    Follower follower;
    double distance;
    double botHeading;
    double botPoseX;
    double botPoseY;
    double newX;
    double newY;

    public PoseGenerator(Limelight m_limelight, Follower m_follower){
        limelight = m_limelight;
        follower = m_follower;
    }

    public Pose calculatePose(){
        distance = limelight.getTargetZ();
        botHeading = follower.pose().heading();
        botPoseX = follower.pose().x();
        botPoseY = follower.pose().y();

        newX = ((Math.cos(botHeading - Math.toRadians(limelight.getTargetX())) * distance) + botPoseX);
        newY = ((Math.sin(botHeading - Math.toRadians(limelight.getTargetX())) * distance) + botPoseY);

        return new Pose(newX, newY);
    }
}
