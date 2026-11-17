package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.OctoQuadConfig;
import com.pedropathing.revhub.localizers.OctoQuadLocalizer;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static SixWheelConfig drivetrainConfig = new SixWheelConfig(c -> {
        c.frontLeftName.set("leftFront");
        c.frontRightName.set("rightFront");
        c.backLeftName.set("leftRear");
        c.backRightName.set("rightRear");
        c.backLeft2Name.set("leftMiddle");
        c.frontRight2Name.set("rightMiddle");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeft2Direction.set(DcMotorSimple.Direction.FORWARD);
        c.frontRight2Direction.set(DcMotorSimple.Direction.REVERSE);
    });

    public static OctoQuadConfig localizerConfig = new OctoQuadConfig(c -> {
        c.name.set("octoquad");
        c.xPodPort.set(0);
        c.yPodPort.set(1);
        c.ticksPerUnit.set(505.316944406);
        c.xPodOffset.set(2.598425196850394);
        c.yPodOffset.set(-7.381889763779528);
        c.xPodDirection.set(OctoQuad.EncoderDirection.REVERSE);
        c.yPodDirection.set(OctoQuad.EncoderDirection.REVERSE);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
        c.i2cRecoveryMode.set(OctoQuad.I2cRecoveryMode.MODE_1_PERIPH_RST_ON_FRAME_ERR);
        c.headingScalar.set(1.018096070474192);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.22744444927454355);
                Controller secondaryTranslationalForward = Controller.proportional(0.08403464657932966);
                Controller primaryTranslationalLateral = Controller.proportional(0.31566653185979343);
                Controller secondaryTranslationalLateral = Controller.proportional(0.1166303487571171);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.007436089118910154));
                c.brake.set(Controller.proportionalFeedforward(0.006320675751073631));

                c.headingFeedback.set(Controller.proportional(4.442987921123168));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.03579000192406689, 0.00926590379093892));

                c.linearBrakeCoefficients.set(Matrix.diag(0.11309191214935967, 0.06495114156934714));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0020221633438776017, 0.0019110091996189663));

                c.maxAchievableForwardVelocity.set(98.65660752518419);
                c.maxAchievableStrafeVelocity.set(78.53564543220473);
                c.naturalForwardDeceleration.set(20.614165419840702);
                c.naturalStrafeDeceleration.set(47.696379022753845);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new OctoQuadLocalizer(h, localizerConfig),
                new SixWheelDrivetrain(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}