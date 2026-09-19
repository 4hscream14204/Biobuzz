package org.firstinspires.ftc.teamcode.base;

import com.pedropathing.math.Pose;

public class DataStorage {
    public static BiobuzzEnums.Alliance alliance = BiobuzzEnums.Alliance.RED;
    public static BiobuzzEnums.RedCell redCell = BiobuzzEnums.RedCell.RED_GENERAL;
    public static BiobuzzEnums.BlueCell blueCell = BiobuzzEnums.BlueCell.BLUE_GENERAL;
    public static BiobuzzEnums.RedCell redCellScoring = BiobuzzEnums.RedCell.RED_CELL_SCORING;
    public static BiobuzzEnums.RedCell redCellAudience = BiobuzzEnums.RedCell.RED_CELL_AUDIENCE;
    public static BiobuzzEnums.BlueCell blueCellScoring = BiobuzzEnums.BlueCell.BLUE_CELL_SCORING;
    public static BiobuzzEnums.BlueCell blueCellAudience = BiobuzzEnums.BlueCell.BLUE_CELL_AUDIENCE;

    public static Pose redCellPoseScoring = new Pose(57.2, 53.5);
    public static Pose redCellPoseAudience = new Pose(57.2, 94);
    public static Pose blueCellPoseScoring = new Pose(83.5, 53.5);
    public static Pose blueCellPoseAudience = new Pose(83.5, 94);

    public static Pose currentCellPose;
}
