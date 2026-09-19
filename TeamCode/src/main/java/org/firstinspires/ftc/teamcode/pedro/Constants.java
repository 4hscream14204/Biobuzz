package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.OTOSConfig;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return null;
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("leftFront");
        c.frontRightName.set("rightFront");
        c.backLeftName.set("leftRear");
        c.backRightName.set("rightRear");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.manualBrakeMode.set(true);
    });
    public static OTOSConfig localizerConfig = new OTOSConfig(c -> {
        c.name.set("sensor_otos");
        c.linearScalar.set(0.9933054599701641);
        c.angularScalar.set(0.9969514606823653);
        c.offset.set(new Pose(-0.0, -0.0));
        c.linearUnit.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.15313652108240805);
                Controller secondaryTranslationalForward = Controller.proportional(0.05657985265674516);
                Controller primaryTranslationalLateral = Controller.proportional(0.29755354448581106);
                Controller secondaryTranslationalLateral = Controller.proportional(0.10993808391036697);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.02221278732964693));
                c.brake.set(Controller.proportionalFeedforward(0.01888086923019989));

                c.headingFeedback.set(Controller.proportional(1.4993731339461476));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04886042269548304, 0.010287824285523958));

                c.linearBrakeCoefficients.set(Matrix.diag(0.07205694406232264, 0.04369377018625153));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.002222713939266682, 0.0027651249352288678));

                c.maxAchievableForwardVelocity.set(44.97696404075497);
                c.maxAchievableStrafeVelocity.set(35.46107833421853);
                c.naturalForwardDeceleration.set(32.79573217847747);
                c.naturalStrafeDeceleration.set(52.991106894552665);
            }
    );
}