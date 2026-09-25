package org.firstinspires.ftc.teamcode.Opmode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.List;

@TeleOp
public class AprilTagClusters extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .build();

        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .build();

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {

            List<AprilTagDetection> currentDetections = tagProcessor.getDetections();
            telemetry.addData("# AprilTags Detected", currentDetections.size());

            for (AprilTagDetection detection : currentDetections) {

                AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;

                if (detection instanceof AprilTagSingleDetection) {
                    //AprilTagDetection tag = tagProcessor.getDetections().get(0);
                    //AprilTagSingleDetection singleDetection = (AprilTagSingleDetection) detection;



                    /*telemetry.addData("x", tag.ftcPose.x);
                    telemetry.addData("y", tag.ftcPose.y);
                    telemetry.addData("z", tag.ftcPose.z);
                    telemetry.addData("roll", tag.ftcPose.roll);
                    telemetry.addData("pitch", tag.ftcPose.pitch);
                    telemetry.addData("yaw", tag.ftcPose.yaw);
                    telemetry.addData("Range", tag.ftcPose.range);
                    telemetry.addData("Bearing", tag.ftcPose.bearing);
                    telemetry.addData("Elevation", tag.ftcPose.elevation);*/

                    //telemetry.addLine(String.format("/n==== (ID %d) %s", singleDetection.id, singleDetection.metadata.name));



                }
                telemetry.addData("Cluster", clusterDet.percentClusterFound);
                telemetry.update();
            }
        }
    }
}
