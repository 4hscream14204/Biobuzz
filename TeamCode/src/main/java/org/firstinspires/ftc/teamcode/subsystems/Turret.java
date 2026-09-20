package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.base.BiobuzzEnums;
import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.screamrobotics.SuperSCREAMLib.controller.PIDController;

public class Turret {
    Servo turretServoL;
    Servo turretServoR;
    public AnalogInput servoEncoder;
    double turretServoPosition;
    Pose goalPose;
    double xSpeed;
    double ySpeed;
    double timeOfFlightMultiplier = /*0.048*/ 0.003;
    double timeOfFlight/* = 0.048*/;
    public double botHeading;
    public double targetHeading;
    double turretOffset;
    double rotationLead;
    double maxDegrees;
    public double degreeNormalized;
    public double degreeModulus;
    public double pidOutputToServoPos;
    public double kD = 0.0005;
    public double kP = 0.005;
    public static double kF = 0.1;
    public static double feedForward = 0.1;
    PIDController turretHeadingPID = new PIDController(kP, 0, kD);

    public Turret(Servo m_turretServoL, Servo m_turretServoR){
        turretServoL = m_turretServoL;
        turretServoR = m_turretServoR;
        //setPosition(0.5);
    }

    public void setPosition(double position){
        turretServoL.setPosition(position);
        turretServoR.setPosition(position);
    }

    public double convertDegToServoPos(double degree){
        degreeNormalized = AngleUnit.normalizeDegrees(degree);
        degreeModulus = degreeNormalized % 360;
        if(degreeModulus < 0){
            degreeModulus += 360;
        }
        if(degreeModulus < 5){
            degreeModulus = 5;
        }
        if(degreeModulus > 350){
            degreeModulus = 350;
        }
        //return ((0.002933 * degreeModulus) - 0.07);
        return ((-0.002840 * degreeModulus) + 1.01666);
    }

    public double getTurretAngle(Follower follower){
        if(DataStorage.alliance == BiobuzzEnums.Alliance.RED){
            if(follower.pose().y() > 72){
                DataStorage.currentCellPose = DataStorage.redCellPoseScoring;
            }
            else{
                DataStorage.currentCellPose = DataStorage.redCellPoseAudience;
            }
        }
        else{
            if(follower.pose().y() > 72){
                DataStorage.currentCellPose = DataStorage.blueCellPoseScoring;
            }
            else{
                DataStorage.currentCellPose = DataStorage.blueCellPoseAudience;
            }
        }
        botHeading = Math.toDegrees(follower.pose().heading());
        xSpeed = follower.velocity().vx;
        ySpeed = follower.velocity().vy;
        timeOfFlight = follower.pose().distance(goalPose) * timeOfFlightMultiplier;
        targetHeading = Math.toDegrees(Math.atan2((goalPose.y() - follower.pose().y() - (ySpeed * timeOfFlight)), (goalPose.x() - follower.pose().x() - (xSpeed * timeOfFlight))));
        turretOffset = targetHeading - botHeading;
        //rotationLead = Math.toDegrees(follower.getAngularVelocity()) * timeOfFlight;
        //turretOffset += rotationLead;
        //turretOffset = ((turretOffset + 180) % 360) -180;
        //turretOffset = Math.max(-maxDegrees, Math.min(maxDegrees, turretOffset));
        return turretOffset;
    }

    public double getTurretAngle(Follower follower, Pose m_goalPose){
        goalPose = m_goalPose;
        botHeading = Math.toDegrees(follower.pose().heading());
        xSpeed = follower.velocity().vx;
        ySpeed = follower.velocity().vy;
        timeOfFlight = follower.pose().distance(goalPose) * timeOfFlightMultiplier;
        targetHeading = Math.toDegrees(Math.atan2((goalPose.y() - follower.pose().y() - (ySpeed * timeOfFlight)), (goalPose.x() - follower.pose().x() - (xSpeed * timeOfFlight))));
        turretOffset = targetHeading - botHeading;
        //rotationLead = Math.toDegrees(follower.getAngularVelocity()) * timeOfFlight;
        //turretOffset += rotationLead;
        //turretOffset = ((turretOffset + 180) % 360) -180;
        //turretOffset = Math.max(-maxDegrees, Math.min(maxDegrees, turretOffset));
        return turretOffset;
    }

    public void setPositionDeg(double positionDeg){
        setPosition(convertDegToServoPos(positionDeg));
        //turretServoR.setPosition(convertDegToServoPos(positionDeg));
        turretServoPosition = convertDegToServoPos(positionDeg);
    }

    public void updatePosition(double headingDeg){
        degreeNormalized = (AngleUnit.normalizeDegrees(headingDeg) + 360);
        degreeModulus = degreeNormalized % 360;
        if(degreeModulus < 5){
            degreeModulus = 5;
        }
        if(degreeModulus > 350){
            degreeModulus = 350;
        }
        double error = (degreeModulus - getPositionDegrees());
        double pidOutput = turretHeadingPID.calculate(error);
        pidOutputToServoPos = ((pidOutput + 1) / 2);
        /*if(Math.abs(error) > 2){
            pidOutputToServoPos -= feedForward * (error/Math.abs(error));
        }*/
        setPosition(pidOutputToServoPos);
    }

    public double getPositionDegrees(){
        return ((-121.448569 * servoEncoder.getVoltage()) + 366.747416);
    }

    public boolean isAtPosition(GoBildaPinpointDriver pinpoint, Follower follower){
        if(Math.abs(getTurretAngle(follower) - getPositionDegrees()) < 5){
            return true;
        }
        return false;
    }
}
