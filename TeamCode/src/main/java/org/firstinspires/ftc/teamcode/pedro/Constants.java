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
        c.xPodOffset.set(2.6377952755905514);
        c.yPodOffset.set(-6.948818897637795);
        c.xPodDirection.set(OctoQuad.EncoderDirection.REVERSE);
        c.yPodDirection.set(OctoQuad.EncoderDirection.REVERSE);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
        c.i2cRecoveryMode.set(OctoQuad.I2cRecoveryMode.MODE_1_PERIPH_RST_ON_FRAME_ERR);
        c.headingScalar.set(1.0180828730443219);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.23102746528745224);
                Controller secondaryTranslationalForward = Controller.proportional(0.08535847525614827);
                Controller primaryTranslationalLateral = Controller.proportional(0.3203590013267415);
                Controller secondaryTranslationalLateral = Controller.proportional(0.118364090840061);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.007696095076377408));
                c.brake.set(Controller.proportionalFeedforward(0.006541680814920797));

                c.headingFeedback.set(Controller.proportional(4.374014655578246));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.07057187532556464, 0.005846466492336231));

                c.linearBrakeCoefficients.set(Matrix.diag(0.09804770385267142, 0.08812704066224866));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0020221137724136967, 0.0014121924448776424));

                c.maxAchievableForwardVelocity.set(99.30857095350618);
                c.maxAchievableStrafeVelocity.set(80.17314385506324);
                c.naturalForwardDeceleration.set(19.366490874218012);
                c.naturalStrafeDeceleration.set(46.3008279013329);
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