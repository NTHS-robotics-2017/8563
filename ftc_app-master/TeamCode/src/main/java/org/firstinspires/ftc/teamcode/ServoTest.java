package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="servotest")

public class ServoTest extends LinearOpMode {

    Servo servo = null;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        servo = hardwareMap.servo.get("servo1");

        waitForStart();

        while (opModeIsActive()) {

            telemetry.update();
            servo.setPosition(0);
            telemetry.addData("Servo Position", servo.getPosition());
            sleep(5000);
            servo.setPosition(1);
            sleep(5000);
            telemetry.addData("Servo Position", servo.getPosition());
            idle();
        }
    }
}
