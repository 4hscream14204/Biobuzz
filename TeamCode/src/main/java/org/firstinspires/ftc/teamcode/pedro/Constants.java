package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.OTOSConfig;
import com.pedropathing.revhub.localizers.OctoQuadConfig;
import com.pedropathing.revhub.localizers.OctoQuadLocalizer;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(new OctoQuadLocalizer(h, localizerConfig),
                new SixWheelDrivetrain(h, drivetrainConfig),
                new Foresight(foresightConfig));
    }
    public static SixWheelConfig drivetrainConfig = new SixWheelConfig(c -> {
        c.frontLeftName.set("frontLeftMotor");
        c.frontRightName.set("frontRightMotor");
        c.backLeftName.set("backLeftMotor");
        c.backRightName.set("backRightMotor");
        c.backLeft2Name.set("middleLeftMotor");
        c.frontRight2Name.set("middleRightMotor");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeft2Direction.set(DcMotorSimple.Direction.REVERSE);
        c.frontRight2Direction.set(DcMotorSimple.Direction.FORWARD);
    });
    public static OctoQuadConfig localizerConfig = new OctoQuadConfig(c -> {
        c.name.set("octoquad");
        c.xPodPort.set(0);
        c.yPodPort.set(1);
        c.ticksPerUnit.set(505.316944406);
        c.xPodOffset.set(3.7401574803149606);
        c.yPodOffset.set(-1.6338582677165356);
        c.xPodDirection.set(OctoQuad.EncoderDirection.FORWARD);
        c.yPodDirection.set(OctoQuad.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
        c.i2cRecoveryMode.set(OctoQuad.I2cRecoveryMode.MODE_1_PERIPH_RST_ON_FRAME_ERR);
        c.headingScalar.set(1.0176178914774523);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.15327146196156072);
                Controller secondaryTranslationalForward = Controller.proportional(0.056629709705905376);
                Controller primaryTranslationalLateral = Controller.proportional(0.21314440068273952);
                Controller secondaryTranslationalLateral = Controller.proportional(0.07875116072899375);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.011012574722608316));
                c.brake.set(Controller.proportionalFeedforward(0.009360688514217069));

                c.headingFeedback.set(Controller.proportional(3.886478112730671));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04216283509297334, 0.0038721087402628527));

                c.linearBrakeCoefficients.set(Matrix.diag(0.05225557045460389, 0.04937756530190966));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0013133761156874208, 0.0013589097371903152));

                c.maxAchievableForwardVelocity.set(93.55464135342787);
                c.maxAchievableStrafeVelocity.set(69.35966313725555);
                c.naturalForwardDeceleration.set(29.750533358091797);
                c.naturalStrafeDeceleration.set(50.528517271291854);
            }
    );
}