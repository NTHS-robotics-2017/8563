/* This opmode is not usable at this time. */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DriveAndGlyphArmTwoController")
public class DriveAndGlyphArmTwoController extends LinearOpMode {

    // Declares robot object to get information from DriveMotors.java, Servos.java
    DriveMotors         motors   = new DriveMotors();
    Servos              servos   = new Servos();

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

// Updates telemetry on phone to show it is initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

// Pulls hardware from robot object
        motors.init(hardwareMap);
        servos.init(hardwareMap);

// Defines variables
        double motorSpeed = 1;
        boolean motorFast = true;
        boolean armFast = true;

// Sets motors to run without encoders for driver operation
        motors.left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motors.right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        servos.claw.setPosition(1);

        waitForStart();

        while (opModeIsActive()) {

// Updates telemetry data
            telemetry.update();
            telemetry.addData("Servo Position: ", servos.claw.getPosition());
            telemetry.addData("Motor Fast: ", motorFast);
            telemetry.addData("Arm Fast: ", armFast);

// Defines if drive motor speed and arm motor speed are fast or slow
            if (gamepad1.left_trigger != 0 && motorFast) {
                motorFast = false;
            } else if (gamepad1.left_trigger != 0 && !motorFast) {
                motorFast = true;
            }

            if (gamepad2.a && armFast) {
                armFast = false;
            } else if (gamepad2.a && !armFast) {
                armFast = true;

            }

// Sets drive motors to joystick locations
            if (motorFast) {
                motors.left.setPower(gamepad1.left_stick_y);
                motors.right.setPower(gamepad1.right_stick_y);
            } else {
                motors.left.setPower(gamepad1.left_stick_y/4);
                motors.right.setPower(gamepad1.right_stick_y/4);
            }

// Sets arm motor to motorSpeed or 0
            if (gamepad2.left_bumper) {
                if (armFast) {
                    motors.arm.setPower(motorSpeed);
                } else {
                    motors.arm.setPower(motorSpeed/6);
                }
            } else if (gamepad2.right_bumper) {
                if (armFast) {
                    motors.arm.setPower(-motorSpeed);
                } else {
                    motors.arm.setPower(-motorSpeed/6);
                }
            } else {
                motors.arm.setPower(0);
            }

// Sets claw servo to open or closed position
            if (gamepad2.right_trigger == 1) {
                servos.claw.setPosition(1);
                Thread.sleep(250);
            } else if (gamepad2.left_trigger == 1) {
                servos.claw.setPosition(.4);
                Thread.sleep(250);
            }
            idle();
        }
    }
}
