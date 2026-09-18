package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import static com.pedropathing.api.Paths.*;

@TeleOp(name = "Ball Detection")
public class BallDetectionTest extends OpMode {
    Follower follower;
    Path generatedPath;
    Pose generatedPose;
    @Override
    public void init() {

    }

    public Path updatePath(){
        return line(follower.pose(), generatedPose).tangent();
    }

    @Override
    public void loop() {
        follower.follow(updatePath());
    }
}
