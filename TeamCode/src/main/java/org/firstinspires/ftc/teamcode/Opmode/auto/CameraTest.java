package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.base.BiobuzzEnums;
import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;

import dev.frozenmilk.util.collections.Cons;

@Autonomous(name = "camera testing")
public class CameraTest extends OpMode {
    RobotBase robotBase;
    ZoneControl zoneControl;
    PolygonZone polygonZone;
    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();
    Pose start = poseFactory.of(51,8,180);

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        follower = Constants.create(hardwareMap);
        polygonZone = new PolygonZone(18, 18);
        zoneControl = new ZoneControl(polygonZone, follower);
       follower.setPose(start);

    }

    @Override
    public void loop() {
        follower.update();
        zoneControl.updateRobotZone();
        robotBase.cameraSubsystem.updateCamera();
        telemetry.addData("Elevation: ", robotBase.cameraSubsystem.getElevation());
        telemetry.addData("Name: ", robotBase.cameraSubsystem.getTagName());
        telemetry.addData("Is Elevated: ", robotBase.cameraSubsystem.IsElevationUp(zoneControl));
        telemetry.addData("Cell Follower:", zoneControl.getCell() );
        telemetry.addData("Alliance", DataStorage.alliance);
    }
}
