/*package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.screamrobotics.SuperSCREAMLib.command.Command;
import org.screamrobotics.SuperSCREAMLib.command.CommandBase;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitUntilCommand;

public class OkLaunchCommand extends SequentialCommandGroup {
    Camera camera;
    RobotBase robotBase;
    Follower follower;

    public OkLaunchCommand(RobotBase m_robotBase, Follower m_follower) {
robotBase = m_robotBase;
follower = m_follower;

        if (camera.isScoringTipped()) {
             new TransferCommand(robotBase, follower);
        } else {
             new WaitUntilCommand(() -> camera.isScoringTipped());
        }
    }
    }

   // public Command YesLaunch() {
     /*   if (camera.isScoringTipped()) {
           return new TransferCommand(robotBase, follower);
        } else {
           return new WaitUntilCommand(() -> camera.isScoringTipped());
        } */
   // }
//}

