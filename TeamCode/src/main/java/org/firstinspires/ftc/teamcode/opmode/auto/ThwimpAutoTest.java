package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Thwimp Auto Test")
public class ThwimpAutoTest extends OpMode {
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start;
    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        start = poseFactory.of(40, 8, 90);
    }

    @Override
    public void start() {
        follower.setPose(start);
    }

    @Override
    public void loop() {
        follower.update();
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
    }
}
