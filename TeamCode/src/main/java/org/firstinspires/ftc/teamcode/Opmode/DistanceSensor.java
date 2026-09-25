package org.firstinspires.ftc.teamcode.Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp
public class DistanceSensor extends OpMode {
    DigitalChannel sensor = hardwareMap.get(DigitalChannel.class, "distanceSensor");

    @Override
    public void init() {
    }

    @Override
    public void loop() {
        boolean isHigh = sensor.getState();
        telemetry.addLine("Detected " + (isHigh ? "Something" : "Nothing"));
        telemetry.update();
    }
}
