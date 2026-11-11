package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Camera;

@Autonomous(name = "Camera Testing")
public class CameraTest extends OpMode {
    RobotBase robotBase;

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
    }

    @Override
    public void loop() {
        robotBase.cameraSubsystem.updateCamera();
        telemetry.addData("Elevation: ", robotBase.cameraSubsystem.getElevation());
        telemetry.addData("Tag Name: ", robotBase.cameraSubsystem.getTagName());
        telemetry.addData("Is Elevated: ", robotBase.cameraSubsystem.IsElevationUp());
    }
}
