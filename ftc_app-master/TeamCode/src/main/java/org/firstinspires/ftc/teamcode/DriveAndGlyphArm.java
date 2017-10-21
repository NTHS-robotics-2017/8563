/* This opmode is not usable at this time. */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DriveAndGlyphArm")
public class DriveAndGlyphArm extends LinearOpMode {

// Declares robot object to get information from DriveMotors.java
    DriveMotors         robot   = new DriveMotors();
    Servos              robotServo   = new Servos();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

// Updates telemetry on phone to show it is initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

// Pulls hardware from robot object
        robot.init(hardwareMap);
        robotServo.init(hardwareMap);

// Defines variables
        double motorSpeed = 1;

// Sets motors to run without encoders for driver operation
        robot.b_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.b_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robotServo.claw.setPosition(0);

        robot.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {

// Updates telemetry data
            telemetry.update();
            telemetry.addData("Servo Position: ", robotServo.claw.getPosition());

// Sets drive motors to joystick locations
            robot.b_l.setPower(gamepad1.left_stick_y);
            robot.b_r.setPower(gamepad1.right_stick_y);

// Sets arm motor to motorSpeed or 0
            if (gamepad1.left_bumper) {
                robot.arm.setPower(motorSpeed);
            } else if (gamepad1.right_bumper) {
                robot.arm.setPower(-motorSpeed);
            } else {
                robot.arm.setPower(0);
            }

// Sets claw servo to open or closed position
            if (gamepad1.right_trigger == 1) {
                robotServo.claw.setPosition(1);
                Thread.sleep(250); //Remove after testing
            } else if (gamepad1.left_trigger == 1) {
                robotServo.claw.setPosition(0);
                Thread.sleep(250); //Remove after testing0
            }
            idle();
        }
    }
}
