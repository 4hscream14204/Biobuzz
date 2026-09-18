package org.firstinspires.ftc.teamcode.pedro.Routes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class ParkAndLeave extends OpMode {
    private Follower follower;
    @Override
    public void init(){
        follower = Constants.create(hardwareMap);

    }

    @Override
    public void start(){

    }
    @Override
    public void loop(){

    }
}
