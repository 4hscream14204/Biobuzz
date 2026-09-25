package org.firstinspires.ftc.teamcode.Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp
public class DistanceSensor extends OpMode {
    DigitalChannel sensor;
    boolean isHigh;

    @Override
    public void init() {
        sensor = hardwareMap.get(DigitalChannel.class, "distanceSensor");
        sensor.setMode(DigitalChannel.Mode.OUTPUT);
    }

    @Override
    public void loop() {
        isHigh = sensor.getState();
        telemetry.addData("Detected ", isHigh);
        telemetry.update();
    }
}
