package org.firstinspires.ftc.teamcode.Opmode;


import static org.firstinspires.ftc.teamcode.base.DataStorage.blueCellPoseAudience;
import static org.firstinspires.ftc.teamcode.base.DataStorage.blueCellPoseScoring;
import static org.firstinspires.ftc.teamcode.base.DataStorage.redCellPoseAudience;
import static org.firstinspires.ftc.teamcode.base.DataStorage.redCellPoseScoring;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.skeletonarmy.marrow.zones.PolygonZone;
import com.skeletonarmy.marrow.zones.Point;

import org.firstinspires.ftc.teamcode.base.BiobuzzEnums;
import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.LaunchCommand;
import org.firstinspires.ftc.teamcode.commands.ToggleCellCommandGroup;
import org.firstinspires.ftc.teamcode.commands.TransferBlockerCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.TransferBlocker;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.button.Trigger;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@Configurable
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
    RobotBase robotBase;
    Follower follower;
    GamepadEx chassisController;
    boolean isFieldCentric = true;

    ElapsedTime timer;
    PolygonZone robotZone = new PolygonZone(18, 18);


    PolygonZone blueScoringSide = new PolygonZone(
            new Point(0, 70),
            new Point(0, 140),
            new Point(140, 140),
            new Point(140, 70)
    );

    PolygonZone redScoringSide = new PolygonZone(
            new Point(0, 0),
            new Point(0, 70),
            new Point(140, 70),
            new Point(140, 0)
    );

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        robotBase = new RobotBase(hardwareMap);
        robotBase.chassisSubsystem.bolFieldCentric = isFieldCentric;
        chassisController = new GamepadEx(gamepad1);
        timer = new ElapsedTime();
        chassisController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new LaunchCommand(robotBase, 1000)));
        chassisController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new TransferBlockerCommand(robotBase, TransferBlocker.TransferBlockerPosition.RELEASE)));
        chassisController.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(new ToggleCellCommandGroup()));


        new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                .whenActive(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(
                                (chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) + 1) / 2
                        ))
                ))
                .whenInactive(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5))
                ));
    }

    @Override
    public void start(){follower.setPose(new Pose(0,0, Math.toRadians(0)));
        timer.reset();
    }

    @Override
    public void loop(){

        robotZone.setPosition(follower.pose().x(), follower.pose().y());
        robotZone.setRotation(follower.pose().heading());

        if(DataStorage.alliance == BiobuzzEnums.Alliance.RED) {
            if (robotZone.isFullyInside(redScoringSide)) {
                DataStorage.currentCellPose = redCellPoseScoring;
            }
            if (robotZone.isFullyInside(blueScoringSide)) {
                DataStorage.currentCellPose = redCellPoseAudience;
            }
        } else {
            if (robotZone.isFullyInside(blueScoringSide)) {
                DataStorage.currentCellPose = blueCellPoseScoring;
            }
            if (robotZone.isFullyInside(redScoringSide)) {
                DataStorage.currentCellPose = blueCellPoseAudience;
            }
        }

        follower.update();
        chassisController.readButtons();
        robotBase.chassisSubsystem.drive(chassisController.getLeftX(), chassisController.getLeftY(), chassisController.getRightX());
        telemetry.addData("Alliance: ", DataStorage.alliance);
        //telemetry.addData("Cell", DataStorage.)
        telemetry.addData("Heading: ", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Position X: ", follower.pose().x());
        telemetry.addData("Position Y: ", follower.pose().y());
        telemetry.addLine("Good luck");
        telemetry.update();



        }
    }

