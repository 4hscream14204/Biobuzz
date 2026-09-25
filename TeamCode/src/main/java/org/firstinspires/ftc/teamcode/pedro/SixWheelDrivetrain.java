package org.firstinspires.ftc.teamcode.pedro;

import android.annotation.SuppressLint;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.drivetrain.Drivetrain;
import com.pedropathing.revhub.drivetrains.CachedMotor;
import com.pedropathing.utils.Utils;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import java.util.HashMap;
import java.util.Map;

public class SixWheelDrivetrain implements Drivetrain{

    public final SixWheelConfig config;
    private final CachedMotor[] motors;
    public final double[] wheelPowers = new double[6];

    private static final int FL = 0;
    private static final int FR = 1;
    private static final int BL = 2;
    private static final int BR = 3;
    private static final int BL2 = 4;
    private static final int FR2 = 5;

    private double powerScale = 1.0;
    private DrivePowers drivePowers = DrivePowers.zero();

    public SixWheelDrivetrain(HardwareMap map, SixWheelConfig config) {
        this.config = config;
        double powerDeadband = config.powerThreshold.get();

        motors = new CachedMotor[]{
                new CachedMotor(map.get(DcMotorEx.class, config.frontLeftName.get()), powerDeadband),
                new CachedMotor(map.get(DcMotorEx.class, config.frontRightName.get()), powerDeadband),
                new CachedMotor(map.get(DcMotorEx.class, config.backLeftName.get()), powerDeadband),
                new CachedMotor(map.get(DcMotorEx.class, config.backRightName.get()), powerDeadband),
                new CachedMotor(map.get(DcMotorEx.class, config.backLeft2Name.get()), powerDeadband),
                new CachedMotor(map.get(DcMotorEx.class, config.frontRight2Name.get()), powerDeadband)
        };

        motors[FL].setDirection(config.frontLeftDirection.get());
        motors[FR].setDirection(config.frontRightDirection.get());
        motors[BL].setDirection(config.backLeftDirection.get());
        motors[BR].setDirection(config.backRightDirection.get());
        motors[BL2].setDirection(config.backLeft2Direction.get());
        motors[FR2].setDirection(config.frontRight2Direction.get());

        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @SuppressLint("DefaultLocale")
    public void applyDrive(DrivePowers powers) {
        drivePowers = powers;

        double[] wheelPowers = computeWheelPowersUnnormalized(powers);

        double maxPower = 1.0;
        for (double power : wheelPowers) {
            maxPower = Math.max(maxPower, Math.abs(power));
        }

        powerScale = 1.0 / maxPower;

        for (int i = 0; i < wheelPowers.length; i++) {
            this.wheelPowers[i] = wheelPowers[i] / maxPower;
            motors[i].setPower(this.wheelPowers[i]);
        }
    }

    public double[] computeWheelPowersUnnormalized(DrivePowers powers) {
        double[] wheelPowers = new double[6];
        double forward = powers.forward();
        double strafe = powers.strafe();
        double turn = powers.turn();

        double fl = forward - strafe - turn;
        double fr = forward + strafe + turn;
        double bl = forward + strafe - turn;
        double br = forward - strafe + turn;

        wheelPowers[FL] = fl;
        wheelPowers[FR] = fr;
        wheelPowers[BL] = bl;
        wheelPowers[BR] = br;
        wheelPowers[BL2] = bl;
        wheelPowers[FR2] = fr;

        return wheelPowers;
    }

    @Override
    public double maxScaling(DrivePowers current, DrivePowers delta) {
        double lambda = 1.0;

        double[] currentPowers = computeWheelPowersUnnormalized(current);
        double[] deltaPowers = computeWheelPowersUnnormalized(delta);

        for (int i = 0; i < 4; i++) {
            double a = currentPowers[i];
            double b = deltaPowers[i];

            if (Math.abs(b) < 1e-9) continue;

            double t1 = (1.0 - a) / b;
            double t2 = (-1.0 - a) / b;

            if (t1 >= 0.0 && t1 < lambda) lambda = t1;
            if (t2 >= 0.0 && t2 < lambda) lambda = t2;
        }

        return Utils.clamp(lambda, 0.0, 1.0);
    }

    @Override
    public void drive(DrivePowers powers, boolean manual) {
        if (manual && config.manualBrakeMode.get())
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        else
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        applyDrive(powers);
    }

    @Override
    public void stop() {
        stop(config.manualBrakeMode.get());
    }

    public void stop(boolean brake) {
        if (brake) {
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        for (CachedMotor motor : motors) {
            motor.setPower(0);
        }
    }

    @Override
    public Map<String, Object> debug() {
        Map<String, Object> map = new HashMap<>();

        map.put("forward", drivePowers.forward());
        map.put("strafe", drivePowers.strafe());
        map.put("turn", drivePowers.turn());
        map.put("powerScale", powerScale);
        map.put("leftFrontWheelPower", wheelPowers[FL]);
        map.put("rightFrontWheelPower", wheelPowers[FR]);
        map.put("leftBackWheelPower", wheelPowers[BL]);
        map.put("rightBackWheelPower", wheelPowers[BR]);

        return map;
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        for (CachedMotor motor : motors) {
            motor.setZeroPowerBehavior(behavior);
        }
    }

    /** Returns the sum of the four motors current in Amps
     * This is not bulk cached by the motors so each motor request is a hardware read
     */
    public double currentAmps() {
        double total = 0;
        for (CachedMotor motor : motors) {
            total += motor.raw().getCurrent(CurrentUnit.AMPS);
        }
        return total;
    }

    @Override
    public double interpolateVelocity(double xRadius, double yRadius, double theta) {
        return 1.0 / (Math.abs(Math.cos(theta)) / xRadius + Math.abs(Math.sin(theta)) / yRadius);
    }
}
