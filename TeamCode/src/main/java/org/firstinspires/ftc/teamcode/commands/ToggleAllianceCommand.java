package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.base.BiobuzzEnums;
import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;

public class ToggleAllianceCommand extends CommandBase {

    @Override
    public void initialize() {
        if(DataStorage.alliance == BiobuzzEnums.Alliance.RED) {
            DataStorage.alliance = BiobuzzEnums.Alliance.BLUE;
        } else {
            DataStorage.alliance = BiobuzzEnums.Alliance.BLUE;
        }
    }
}

