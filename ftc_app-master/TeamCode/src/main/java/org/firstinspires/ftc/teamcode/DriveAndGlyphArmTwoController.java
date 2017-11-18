
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.DriveMotors;
import org.firstinspires.ftc.teamcode.Hardware.Servos;

@Disabled
@TeleOp(name="DriveAndGlyphArmTwoController")
public class DriveAndGlyphArmTwoController extends LinearOpMode {

    // Declares robot object to get information from DriveMotors.java, Servos.java
    DriveMotors motors   = new DriveMotors();
    Servos servos   = new Servos();

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
        boolean motorFast = true;
        boolean armFast = true;
        boolean buttonARelease = true;
        boolean leftBumperRelease = true;
        boolean buttonAActive;
        boolean leftBumperActive;
        double currentRuntime;
        double endRuntimeA = 0;
        double endRuntimeBumper = 0;

// Sets motors to run without encoders for driver operation
        motors.left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motors.right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        servos.claw.setPosition(1);

        waitForStart();

        while (opModeIsActive()) {

// Sets currentRuntime to the current elapsed time
            currentRuntime = Math.round(System.nanoTime()/1E6);

// Updates telemetry data
            telemetry.update();
            telemetry.addData("Servo Position: ", servos.claw.getPosition());
            telemetry.addData("Motor Fast: ", motorFast);
            telemetry.addData("Arm Fast: ", armFast);
            telemetry.addData("Current Runtime: ", currentRuntime);
            telemetry.addData("End Runtime Button A: ", endRuntimeA);
            telemetry.addData("End Runtime Left Bumper: ", endRuntimeBumper);

// Sets button and trigger to active if it has been 2 seconds
            if (currentRuntime >= endRuntimeA) {
                buttonAActive = true;
            } else {
                buttonAActive = false;
            }
            if (currentRuntime >= endRuntimeBumper) {
                leftBumperActive = true;
            } else {
                leftBumperActive = false;
            }

// Defines if drive motor speed and arm motor speed are fast or slow
            if (gamepad1.left_bumper && leftBumperActive) {
                if (leftBumperRelease) {
                    motorFast = !motorFast;
                    endRuntimeBumper = currentRuntime+1000;
                }
            }

            if (gamepad2.a && buttonAActive) {
                if (buttonARelease) {
                    armFast = !armFast;
                    endRuntimeA = currentRuntime+1000;
                }
            }

// Sets drive motors to joystick locations
            if (motorFast) {
                motors.left.setPower(gamepad1.left_stick_y);
                motors.right.setPower(gamepad1.right_stick_y);
            } else {
                motors.left.setPower(gamepad1.left_stick_y/4);
                motors.right.setPower(gamepad1.right_stick_y/4);
            }

// Sets arm motor to joystick position of gamepad 2
            motors.arm.setPower(gamepad2.left_stick_y);

// Sets claw servo to open or closed position
            if (gamepad2.right_trigger == 1) {
                servos.claw.setPosition(1);
            } else if (gamepad2.left_trigger == 1) {
                servos.claw.setPosition(.4);
            }
            idle();
        }
    }
}
