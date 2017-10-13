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

// Defines arm servos and sets to starting position
        int armPosition = 0;

        int openClawPosition = 0;
        double closeClawPosition = 0.5;

        boolean clawStatus = false;

// Sets motors to run without encoders for driver operation
        robot.b_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.b_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robotServo.servoClaw.setPosition(openClawPosition);

        waitForStart();

        while (opModeIsActive()) {

// Sets robot to operate using controller
            telemetry.update();
            robot.b_l.setPower(gamepad1.left_stick_y);
            robot.b_r.setPower(gamepad1.right_stick_y);
            if (gamepad1.right_trigger == 1) {
                robotServo.servoClaw.setPosition(closeClawPosition);
            }
            if (gamepad1.left_trigger == 1) {
                robotServo.servoClaw.setPosition(openClawPosition);
            }
            idle();
        }
    }
}
