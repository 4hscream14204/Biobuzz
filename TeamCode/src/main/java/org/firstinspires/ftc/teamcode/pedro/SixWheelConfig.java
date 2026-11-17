package org.firstinspires.ftc.teamcode.pedro;

import static com.pedropathing.config.Validator.nonnegative;

import com.pedropathing.config.ConfigVar;
import com.pedropathing.config.Configuration;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class SixWheelConfig {
    public final ConfigVar<String> frontLeftName = ConfigVar.required();
    public final ConfigVar<String> backLeftName = ConfigVar.required();
    public final ConfigVar<String> frontRightName = ConfigVar.required();
    public final ConfigVar<String> backRightName = ConfigVar.required();
    public final ConfigVar<String> backLeft2Name = ConfigVar.required();
    public final ConfigVar<String> frontRight2Name = ConfigVar.required();
    public final ConfigVar<DcMotorSimple.Direction> frontLeftDirection = ConfigVar.required();
    public final ConfigVar<DcMotorSimple.Direction> backLeftDirection = ConfigVar.required();
    public final ConfigVar<DcMotorSimple.Direction> frontRightDirection = ConfigVar.required();
    public final ConfigVar<DcMotorSimple.Direction> backRightDirection = ConfigVar.required();
    public final ConfigVar<DcMotorSimple.Direction> backLeft2Direction = ConfigVar.required();
    public final ConfigVar<DcMotorSimple.Direction> frontRight2Direction = ConfigVar.required();

    /** Whether ZeroPowerBrake mode is enabled in manual mode. */
    public final ConfigVar<Boolean> manualBrakeMode = ConfigVar.of(true);

    /** Smallest power change that triggers a hardware write. */
    public final ConfigVar<Double> powerThreshold = ConfigVar.of(0.01, nonnegative());

    public SixWheelConfig(Configuration<SixWheelConfig> config) {
        config.configure(this);
    }
}
