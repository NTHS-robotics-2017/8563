package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.DriveMotors;

@TeleOp(name="BasicDrive")
public class BasicDrive extends LinearOpMode {

// Declares robot object to get information from DriveMotors.java
    DriveMotors motors   = new DriveMotors();

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

// Updates telemetry on phone to show it is initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

// Pulls hardware from robot object
        motors.init(hardwareMap);

// Sets motors to run without encoders for driver operation
        motors.left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motors.right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

// Sets robot to operate using controller
            telemetry.update();
            motors.left.setPower(gamepad1.left_stick_y);
            motors.right.setPower(gamepad1.right_stick_y);
            idle();
        }
    }
}
