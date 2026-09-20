package org.firstinspires.ftc.teamcode.pedro.procedures;

import com.pedropathing.tuning.autotune.*;
import com.pedropathing.tuning.autotune.Display.FourWheelBot.Wheel;
import com.qualcomm.robotcore.hardware.DcMotor;

enum SixWheelDirection {
    @DisplayName("Forward") FORWARD,
    @DisplayName("Reversed") REVERSE
}

public class SixWheelTuner extends Procedure {
    public SixWheelTuner() {
        super("6 Wheel Mecanum Tuner", "A procedure to find the directions of 6 mecanum wheels.");
    }

    @Override
    public void run() throws InterruptedException {
        Inputs motorNames = inputs("Mecanum Motor Names", "Enter the names in HardwareMap of your drivetrain motors.");
        Inputs.Field<String> frontLeftName = motorNames.s("Front Left Name");
        Inputs.Field<String> frontRightName = motorNames.s("Front Right Name");
        Inputs.Field<String> backLeftName = motorNames.s("Back Left Name");
        Inputs.Field<String> backRightName = motorNames.s("Back Right Name");
        Inputs.Field<String> backLeft2Name = motorNames.s("Back Left 2 Name");
        Inputs.Field<String> frontRight2Name = motorNames.s("Front Right 2 Name");
        awaitInputs(motorNames);

        confirmation("Motor Directions", "Each drivetrain motor will spin, one at a time. After each one, you will enter whether it spun forward or reversed. You may use the interactive diagram to see which wheel should be spinning and which direction is forward.");

        SixWheelDirection frontLeftDirection = testMotor(Wheel.FRONT_LEFT, "Front Left", frontLeftName.get());
        SixWheelDirection frontRightDirection = testMotor(Wheel.FRONT_RIGHT, "Front Right", frontRightName.get());
        SixWheelDirection backLeftDirection = testMotor(Wheel.BACK_LEFT, "Back Left", backLeftName.get());
        SixWheelDirection backRightDirection = testMotor(Wheel.BACK_RIGHT, "Back Right", backRightName.get());
        SixWheelDirection backLeft2Direction = testMotor(Wheel.BACK_LEFT, "Back Left 2", backLeftName.get());
        SixWheelDirection frontRight2Direction = testMotor(Wheel.FRONT_RIGHT, "Front Right 2", backRightName.get());

        result("frontLeftName", frontLeftName.get());
        result("frontRightName", frontRightName.get());
        result("backLeftName", backLeftName.get());
        result("backRightName", backRightName.get());
        result("backLeft2Name", backLeft2Name.get());
        result("frontRight2Name", frontRight2Name.get());
        result("frontLeftDirection", frontLeftDirection);
        result("frontRightDirection", frontRightDirection);
        result("backLeftDirection", backLeftDirection);
        result("backRightDirection", backRightDirection);
        result("backLeft2Direction", backLeft2Direction);
        result("frontRight2Direction", frontRight2Direction);

        code(Language.JAVA, "public static SixWheelConfig drivetrainConfig = new SixWheelConfig(c -> {\n" +
                "    c.frontLeftName.set(\"" + frontLeftName.get() + "\");\n" +
                "    c.frontRightName.set(\"" + frontRightName.get() + "\");\n" +
                "    c.backLeftName.set(\"" + backLeftName.get() + "\");\n" +
                "    c.backRightName.set(\"" + backRightName.get() + "\");\n" +
                "    c.backLeft2Name.set(\"" + backLeft2Name.get() + "\");\n" +
                "    c.frontRight2Name.set(\"" + frontRight2Name.get() + "\");\n" +
                "    c.frontLeftDirection.set(DcMotorSimple.Direction." + frontLeftDirection + ");\n" +
                "    c.frontRightDirection.set(DcMotorSimple.Direction." + frontRightDirection + ");\n" +
                "    c.backLeftDirection.set(DcMotorSimple.Direction." + backLeftDirection + ");\n" +
                "    c.backRightDirection.set(DcMotorSimple.Direction." + backRightDirection + ");\n" +
                "    c.backLeft2Direction.set(DcMotorSimple.Direction." + backLeft2Direction + ");\n" +
                "    c.frontRight2Direction.set(DcMotorSimple.Direction." + frontRight2Direction + ");\n" +
                "});");
    }

    private SixWheelDirection testMotor(Wheel wheel, String displayName, String hardwareName) throws InterruptedException {
        final boolean[] correctMotor = new boolean[1];
        final SixWheelDirection[] direction = new SixWheelDirection[1];

        withDisplay(Display.fourWheelBot(wheel, false), () -> {
            runOpMode(new SpinMotor(displayName, hardwareName));

            Inputs inputs = inputs(displayName, "Determine the " + displayName.toLowerCase() + " motor direction.");
            Inputs.Field<Boolean> correctMotorField = inputs.b("Did the " + displayName.toLowerCase() + " motor spin?").withDefault(true);
            Inputs.Field<SixWheelDirection> directionField = inputs.e("Which way did the motor spin?", SixWheelDirection.class);
            awaitInputs(inputs);

            correctMotor[0] = correctMotorField.get();
            direction[0] = directionField.get();
        });

        if (!correctMotor[0])
            abort("The wrong motor spun. Check that your motors are plugged into the correct ports, and that they are configured correctly. Then, try again.");

        return direction[0];
    }
}

class SixWheelSpinMotor extends TuningOpMode<Void> {
    private final String name;

    public SixWheelSpinMotor(String displayName, String hardwareName) {
        super(displayName, "The " + displayName.toLowerCase() + " motor will spin. The interactive diagram shows which way is forward. Click stop when you know if it is spinning forward or reversed.", true);
        this.name = hardwareName;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    protected Void runTuningOpMode() {
        DcMotor motor = hardwareMap.dcMotor.get(name);
        waitForStart();
        motor.setPower(0.5);
        while (opModeIsActive()) {
        }
        motor.setPower(0);
        return null;
    }
}
