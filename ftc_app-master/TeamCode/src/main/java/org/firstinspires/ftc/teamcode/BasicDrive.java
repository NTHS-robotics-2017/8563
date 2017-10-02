package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="BasicDrive")

public class BasicDrive extends LinearOpMode
{
    //Declares robot object to get information from DriveMotors.java
    DriveMotors         robot   = new DriveMotors();

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        //Updates telemetry on phone to show it is initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Pulls hardware from robot object
        robot.init(hardwareMap);

        //Sets motors to run without encoders for driver operation
        robot.f_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.b_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.f_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.b_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        while (opModeIsActive())
        {
            //Sets robot to operate using controller
            telemetry.update();
            robot.f_l.setPower(gamepad1.left_stick_y);
            robot.b_l.setPower(gamepad1.left_stick_y);
            robot.f_r.setPower(gamepad1.right_stick_y );
            robot.b_r.setPower(gamepad1.right_stick_y);
            idle();
        }

    }
}
