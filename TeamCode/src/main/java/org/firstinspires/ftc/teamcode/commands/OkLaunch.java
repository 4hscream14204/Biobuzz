package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.screamrobotics.SuperSCREAMLib.command.Command;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;
import org.screamrobotics.SuperSCREAMLib.command.WaitUntilCommand;

public class OkLaunch extends CommandBase {
    Camera camera;
    RobotBase robotBase;
    Follower follower;

    public OkLaunch(RobotBase m_robotBase, Follower follower) {

    }

    public Command YesLaunch() {
        if (camera.isScoringTipped() == true) {
           return new TransferCommand(robotBase, follower);
        } else {
           return new WaitUntilCommand(() -> camera.isScoringTipped());
        }
    }
}
