package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import static com.pedropathing.api.Paths.*;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.pedro.PoseGenerator;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;

@TeleOp(name = "Ball Detection")
public class BallDetectionTest extends OpMode {
    Follower follower;
    PoseGenerator poseGenerator;
    Path generatedPath;
    Pose generatedPose;
    Limelight limelight;
    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        limelight = new Limelight(hardwareMap.get(Limelight3A.class, "limelight"));
        poseGenerator = new PoseGenerator(limelight, follower);
    }

    @Override
    public void loop() {
        follower.update();
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Pose X", poseGenerator.calculatePose().x());
        telemetry.addData("Pose Y", poseGenerator.calculatePose().y());
    }
}
