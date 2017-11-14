
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.DriveMotors;
import org.firstinspires.ftc.teamcode.Hardware.Servos;

@Disabled
@TeleOp(name="MainTeleOpTwoController")
public class MainTeleOpTwoController extends LinearOpMode {

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
        boolean leftBumperRelease = true;
        boolean leftBumperActive;
        double currentRuntime;
        double endRuntimeBumper = 0;

// Claw servo positions
        double leftClawOpen = 1;
        double rightClawOpen = 0;
        double leftClawClose = 0;
        double rightClawClose = 1;

// Sets motors to run without encoders for driver operation
        motors.left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motors.right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        servos.clawLeft.setPosition(leftClawOpen);
        servos.clawRight.setPosition(rightClawOpen);

        waitForStart();

        while (opModeIsActive()) {

// Sets currentRuntime to the current elapsed time
            currentRuntime = Math.round(System.nanoTime()/1E6);

// Updates telemetry data
            telemetry.update();
            telemetry.addData("Left Claw Servo Position: ", servos.clawLeft.getPosition());
            telemetry.addData("Right Claw Servo Position: ", servos.clawRight.getPosition());
            telemetry.addData("Motor Fast: ", motorFast);
            telemetry.addData("Arm Fast: ", armFast);
            telemetry.addData("Current Runtime: ", currentRuntime);
            telemetry.addData("End Runtime Left Bumper: ", endRuntimeBumper);

// Sets button and trigger to active if it has been 1 seconds
            if (currentRuntime >= endRuntimeBumper) {
                leftBumperActive = true;
            } else {
                leftBumperActive = false;
            }

// Defines if drive motor speed speed are fast or slow
            if (gamepad1.left_bumper && leftBumperActive) {
                if (leftBumperRelease) {
                    motorFast = !motorFast;
                    endRuntimeBumper = currentRuntime+1000;
                }
            }

// Sets drive motors to joystick position of gamepad 1
            if (motorFast) {
                motors.left.setPower(gamepad1.left_stick_y);
                motors.right.setPower(gamepad1.right_stick_y);
            } else {
                motors.left.setPower(gamepad1.left_stick_y/4);
                motors.right.setPower(gamepad1.right_stick_y/4);
            }

// Sets arm motor to left joystick position of gamepad 2
            motors.arm.setPower(gamepad2.left_stick_y);

// Sets claw servo to open or closed position
            if (gamepad2.right_trigger == 1) {
                servos.clawLeft.setPosition(leftClawOpen);
                servos.clawRight.setPosition(rightClawOpen);
            } else if (gamepad2.left_trigger == 1) {
                servos.clawLeft.setPosition(leftClawClose);
                servos.clawRight.setPosition(rightClawClose);
            }


            idle();
        }
    }
}
