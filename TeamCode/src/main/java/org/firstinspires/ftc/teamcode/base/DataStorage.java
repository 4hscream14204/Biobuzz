package org.firstinspires.ftc.teamcode.base;

import com.pedropathing.math.Pose;

public class DataStorage {
    public static BiobuzzEnums.Alliance alliance = BiobuzzEnums.Alliance.RED;

    public static Pose redCellPoseScoring = new Pose(57.2, 53.5);
    public static Pose redCellPoseAudience = new Pose(57.2, 94);
    public static Pose blueCellPoseScoring = new Pose(83.5, 53.5);
    public static Pose blueCellPoseAudience = new Pose(83.5, 94);

    public static Pose currentCellPose;
}
