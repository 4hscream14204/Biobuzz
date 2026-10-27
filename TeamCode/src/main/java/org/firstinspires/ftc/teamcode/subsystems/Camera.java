package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.List;

public class Camera {

    AprilTagProcessor tagProcessor;
    List<AprilTagDetection> currentDetections;
    AprilTagClusterDetection clusterDet;

    double elevation;
    double pitch;
    boolean launch;
    String name;

    public Camera(WebcamName m_webcam){
        tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .build();

        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(m_webcam)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .build();
    }

    public void updateCamera(){
        currentDetections = tagProcessor.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            clusterDet = (AprilTagClusterDetection) detection;
            elevation = clusterDet.ftcPose.elevation;
            pitch = clusterDet.ftcPose.pitch;
            name = clusterDet.metadata.shortName;
        }
    }

    public String getTagName(){
        return name;
    }

    public int getPercentageCluster(){
        return clusterDet.percentClusterFound;
    }

    public double getElevation(){
        return elevation;
    }

    /*public boolean okLaunch() {
        if (isScoringTipped() == true) {

        }
        else {
            launch = false;
        }
        return launch;
    }*/

    public double getPitch(){
        return pitch;
    }

    public boolean isScoringTipped(){

        if(getTagName() == "RED SCORING" && getElevation() > 15){
            return true;
        }
        if(getTagName() == "RED AUDIENCE" && getElevation() > 15){
            return true;
        } else
            return false;

    }

}
