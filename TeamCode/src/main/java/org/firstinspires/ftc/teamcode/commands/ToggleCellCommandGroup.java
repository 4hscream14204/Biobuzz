package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.base.BiobuzzEnums;
import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class ToggleCellCommandGroup extends CommandBase {
    @Override
    public void initialize() {
        if(DataStorage.alliance == BiobuzzEnums.Alliance.RED) {
            if(DataStorage.redCell == BiobuzzEnums.RedCell.RED_CELL_SCORING) {
                DataStorage.redCell = BiobuzzEnums.RedCell.RED_CELL_AUDIENCE;
            }
            else {
                DataStorage.redCell = BiobuzzEnums.RedCell.RED_CELL_SCORING;
            }
        }
        else {
            if(DataStorage.blueCell == BiobuzzEnums.BlueCell.BLUE_CELL_SCORING) {
                DataStorage.blueCell = BiobuzzEnums.BlueCell.BLUE_CELL_AUDIENCE;
            }
            else {
                DataStorage.blueCell = BiobuzzEnums.BlueCell.BLUE_CELL_SCORING;
            }
        }

    }
}

