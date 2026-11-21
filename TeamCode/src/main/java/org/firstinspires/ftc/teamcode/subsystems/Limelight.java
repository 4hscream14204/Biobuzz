package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import java.util.List;
public class Limelight extends OpMode {
    public double distance;
    com.qualcomm.hardware.limelightvision.Limelight3A limelight;
    Follower follower;
    public double id;
    public Pose3D botPose;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.start();
        limelight.pipelineSwitch(0);
        follower = Constants.create(hardwareMap);

    }

    @Override
    public void loop() {
        LLResult result = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> aprilTagResult = result.getFiducialResults();
        for (LLResultTypes.FiducialResult fr : aprilTagResult) {
            if (result != null && result.isValid()) {
                result.getPipelineIndex();
                double tx = result.getTx();
                double ty = result.getTy();
                double ta = result.getTa();
                telemetry.addData("Target X", tx);
                telemetry.addData("Target Y", ty);
                telemetry.addData("Target Area", ta);
                distance = fr.getTargetPoseRobotSpace().getPosition().z;
            } else {
                telemetry.addData("Limelight", "NoTargets");
            }
            double[] inputs = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0., 8};
            limelight.updatePythonInputs(inputs);
            double[] pythonOutputs = result.getPythonOutput();
            if (pythonOutputs != null && pythonOutputs.length > 0) ;
            {
                double firstOutput = pythonOutputs[0];
                telemetry.addData("Python output:", firstOutput);
            }
            double robotYaw = follower.pose().heading();
            limelight.updateRobotOrientation(robotYaw);
            if (result != null && result.isValid()) ;
            {
                Pose3D botpose_mt2 = result.getBotpose_MT2();
                if (botpose_mt2 != null) ;
                {
                    double x = botpose_mt2.getPosition().x;
                    double y = botpose_mt2.getPosition().y;
                    telemetry.addData("MT2 location:", "(" + x + ", " + y + ")");
                }
            }
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fiducial : fiducials) {
                for (LLResultTypes.FiducialResult fiducialAprilTag : fiducials) {
                    int idAprilTag = fiducialAprilTag.getFiducialId();
                    int id = fiducial.getFiducialId();
                    double x = fiducial.getTargetXDegrees();
                    double y = fiducial.getTargetYDegrees();
                    double StrafeDistance_3DZ = fiducialAprilTag.getRobotPoseTargetSpace().getPosition().z;
                    double StrafeDistance_3DX = fiducialAprilTag.getRobotPoseTargetSpace().getPosition().x;
                    double StrafeDistance_3DY = fiducialAprilTag.getRobotPoseTargetSpace().getPosition().y;
                    telemetry.addData("Fiducial" + id, "is" + distance + "meters away");
                }
                List<LLResultTypes.DetectorResult> detections = result.getDetectorResults();
                for (LLResultTypes.DetectorResult detection : detections) {
                    String className = detection.getClassName();
                    double x = detection.getTargetXDegrees();
                    double y = detection.getTargetYDegrees();
                    telemetry.addData(className, "at (" + x + ", " + y + ") degrees");
                }
            }
        }
    }
}

