package org.firstinspires.ftc.teamcode.Opmode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.BiobuzzEnums;
import org.firstinspires.ftc.teamcode.commands.ToggleAllianceCommand;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;
import org.screamrobotics.SuperSCREAMLib.hardware.RGBLight;

@TeleOp(name = "laserDigitalExample")
public class TheDistanceSensor extends OpMode {

    DigitalChannel laserInput;
    RGBLight light;
    boolean stateHigh;
    boolean lastState;
    boolean isRed;
    int pollenCounter;
    GamepadEx gamepad;
    @Override
    public void init() {
        // Get the digital sensor from the hardware map
        laserInput = hardwareMap.get(DigitalChannel.class, "distanceSensor");
        light = new RGBLight(hardwareMap.servo.get("distanceLight"));

        gamepad = new GamepadEx(gamepad1);

        isRed = true;

        //gamepad.getGamepadButton(GamepadKeys.Button.CIRCLE)
              //  .whenActive(()-> CommandScheduler.getInstance().schedule(new ToggleAllianceCommand()));
        gamepad.getGamepadButton(GamepadKeys.Button.CIRCLE).whenActive(()-> isRed = !isRed);
    }

    @Override
    public void loop() {
        lastState = stateHigh;
        stateHigh = laserInput.getState();

        if (!lastState && stateHigh) pollenCounter++;

        //if (pollenCounter < 4) light.setColor(RGBLight.RGBLightColors.PURPLE);
        //else if (pollenCounter > 4) pollenCounter = 0;
        //else if (pollenCounter == 4) light.setColor(RGBLight.RGBLightColors.YELLOW);
        if (isRed) {
            if (pollenCounter > 4) pollenCounter = 0;
            if (pollenCounter == 4) light.setColor(RGBLight.RGBLightColors.RED);
            else light.setColor(RGBLight.RGBLightColors.PURPLE);
        }
        else {
            if (pollenCounter > 4) pollenCounter = 0;
            if (pollenCounter == 4) light.setColor(RGBLight.RGBLightColors.BLUE);
            else light.setColor(RGBLight.RGBLightColors.PURPLE);
        }

        telemetry.addData("THE Pollen Counter: ", pollenCounter);
        telemetry.addData("THE Raw (HIGH/LOW)", stateHigh);
        telemetry.addData("Is red", isRed);
        telemetry.update();
    }
}