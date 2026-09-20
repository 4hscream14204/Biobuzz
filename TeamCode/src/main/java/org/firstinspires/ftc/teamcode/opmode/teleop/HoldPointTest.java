package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name = "Hold Point Test")
public class HoldPointTest extends OpMode {

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
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                follower.pose().heading()
        );
        ManualDrive.driveOrHold(follower, powers);
        follower.update();
    }
}
