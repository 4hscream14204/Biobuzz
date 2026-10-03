package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.base.ZoneControl;
import org.firstinspires.ftc.teamcode.commands.FlowerIntakeCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;
import org.screamrobotics.SuperSCREAMLib.command.button.Trigger;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@TeleOp(name = "Best Teleop Ever")
public class BestTeleopEver extends OpMode {
    RobotBase robotBase;
    GamepadEx gamepadEx1;
    GamepadEx gamepadEx2;
    Follower follower;
    int velocity = 1000;
    SequentialCommandGroup flowerIntake;
    ZoneControl zoneControl;
    PolygonZone robotZone = new PolygonZone(18, 18);
    ZoneControl.Flower flower;

    boolean isFlowerLock;

    PolygonZone redScoringFlowerZone = new PolygonZone(
            new Point(47, 0),
            new Point(0, 94),
            new Point(0, 0),
            new Point(47, 94)
    );
    PolygonZone blueAudienceFlowerZone = new PolygonZone(
            new Point(47, 0),
            new Point(144, 47),
            new Point(144, 0),
            new Point(47, 47)
    );
    PolygonZone redAudienceFlowerZone = new PolygonZone(
            new Point(0, 144),
            new Point(94, 144),
            new Point(94, 94),
            new Point(0, 94)
    );
    PolygonZone blueScoringFlowerZone = new PolygonZone(
            new Point(94, 47),
            new Point(94, 144),
            new Point(144, 47),
            new Point(144, 144)
    );



    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        CommandScheduler.getInstance().reset();
        robotBase = new RobotBase(hardwareMap);
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        zoneControl.updateRobotZone();
        isFlowerLock = false;

        //Intake
        new Trigger(() -> gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(() -> gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                .whileActiveContinuous(() -> robotBase.intakeSubsystem.setPower(gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)))
                .whenInactive(() -> robotBase.intakeSubsystem.setPower(0));

        flowerIntake = new SequentialCommandGroup(
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(1)),
                new WaitCommand(5000),
                new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0))
        );

        //Launch
        /*gamepad.getGamepadButton(GamepadKeys.Button.CROSS)
                .whenActive(() -> CommandScheduler.getInstance().schedule(new LaunchCommand(robotBase, velocity)));*/
        gamepadEx1.getGamepadButton(GamepadKeys.Button.CROSS)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new InstantCommand(() -> robotBase.windmillSubsystem.setPower(1))))
                .whenReleased(() -> CommandScheduler.getInstance().schedule(new InstantCommand(() -> robotBase.windmillSubsystem.setPower(0.5))));

        //Tune launcher
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(() -> robotBase.launcherSubsystem.setVelocity(robotBase.launcherSubsystem.getVelocity() + 100));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> robotBase.launcherSubsystem.setVelocity(robotBase.launcherSubsystem.getVelocity() - 100));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(() -> robotBase.launcherSubsystem.setVelocity(velocity));

        gamepadEx1.getGamepadButton(GamepadKeys.Button.SQUARE)
                .whenActive(() -> CommandScheduler.getInstance().schedule(flowerIntake));

        // Flower "heading lock"

        gamepadEx2.getGamepadButton(GamepadKeys.Button.SQUARE)
                .whenPressed(() -> isFlowerLock = !isFlowerLock);

        new Trigger(() -> zoneControl.getFlower() == ZoneControl.Flower.BLUEAUDIENCEFLOWER)
                .whenActive(() -> robotBase.chassisSubsystem.setHeading(270));

        new Trigger(() -> zoneControl.getFlower() == ZoneControl.Flower.BLUESCORINGFLOWER)
                .whenActive(() -> robotBase.chassisSubsystem.setHeading(0));

        new Trigger(() -> zoneControl.getFlower() == ZoneControl.Flower.REDAUDIENCEFLOWER)
                .whenActive(() -> robotBase.chassisSubsystem.setHeading(90));

        new Trigger(() -> zoneControl.getFlower() == ZoneControl.Flower.REDSCORINGFLOWER)
                .whenActive(() -> robotBase.chassisSubsystem.setHeading(180));
    }

    @Override
    public void init_loop() {
        follower.update();
        zoneControl.updateRobotZone();
    }

    @Override
    public void start() {
        robotBase.launcherSubsystem.setVelocity(velocity);
        CommandScheduler.getInstance().schedule(new FlowerIntakeCommand(zoneControl, robotBase, follower));

    }

    @Override
    public void loop() {
        follower.update();
        gamepadEx1.readButtons();
        zoneControl.updateRobotZone();
        flower = zoneControl.getFlower();
        robotBase.chassisSubsystem.drive(gamepadEx1.getLeftX(), gamepadEx1.getLeftY(), gamepadEx1.getRightX(), true, isFlowerLock, follower);
        CommandScheduler.getInstance().run();
        telemetry.addData("Current launch velocity: ", robotBase.launcherSubsystem.getVelocity());
    }
}
