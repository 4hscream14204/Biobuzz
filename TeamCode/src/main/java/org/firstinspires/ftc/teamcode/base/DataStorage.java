package org.firstinspires.ftc.teamcode.base;

import com.pedropathing.math.Pose;

public class DataStorage {
    public static BiobuzzEnums.Alliance alliance = BiobuzzEnums.Alliance.RED;

    public static Pose redCellPoseScoring = new Pose(64, 85);
    public static Pose redCellPoseAudience = new Pose(64, 60);
    public static Pose blueCellPoseScoring = new Pose(83.5, 85);
    public static Pose blueCellPoseAudience = new Pose(83.5, 60);

    public static Pose currentCellPose = redCellPoseScoring;
}
