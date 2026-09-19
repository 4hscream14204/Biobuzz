package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import static com.pedropathing.api.Paths.*;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.pedro.PoseGenerator;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.FollowPathCommand;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@TeleOp(name = "Ball Detection")
public class BallDetectionTest extends OpMode {
    Follower follower;
    PoseGenerator poseGenerator;
    Path generatedPath;
    Pose generatedPose;
    Limelight limelight;
    GamepadEx gamepad;
    boolean manualDrive = true;
    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        gamepad = new GamepadEx(gamepad1);
        limelight = new Limelight(hardwareMap.get(Limelight3A.class, "limelight"));
        limelight.initLimelight(Limelight.limelightPipelines.POLLEN);
        poseGenerator = new PoseGenerator(limelight, follower);
        follower.setPose(new Pose(10, 10, 0));
        generatedPath = line(new Pose(10, 10, 0), new Pose(11, 10, 0)).tangent();
    }

    @Override
    public void init_loop() {
        follower.update();
        limelight.updateLimelight();
        updatePath();
    }

    private Path updatePath(){
        return line(follower.pose(), poseGenerator.calculatePose()).constant(follower.pose().heading());
    }

    @Override
    public void loop() {
        generatedPath = updatePath();
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                follower.pose().heading()
        );
        if(gamepad1.aWasPressed()){
            manualDrive = false;
            follower.follow(generatedPath);
        }
        if(!follower.isBusy()){
            manualDrive = true;
        }
        limelight.updateLimelight();
        gamepad.readButtons();
        /*if(manualDrive){
            follower.manual(powers);
        }*/
        follower.update();
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Pose X", poseGenerator.calculatePose().x());
        telemetry.addData("Pose Y", poseGenerator.calculatePose().y());
        CommandScheduler.getInstance().run();
    }
}
