package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="BasicDrive")

public class BasicDrive extends LinearOpMode
{
    DcMotor Motor1 = null;
    DcMotor Motor2 = null;
    DcMotor Motor3 = null;
    DcMotor Motor4 = null;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        Motor1 = hardwareMap.dcMotor.get("m_f_l");
        Motor2 = hardwareMap.dcMotor.get("m_b_l");
        Motor3 = hardwareMap.dcMotor.get("m_f_r");
        Motor4 = hardwareMap.dcMotor.get("m_b_r");
        Motor3.setDirection(DcMotor.Direction.REVERSE);
        Motor4.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive())
        {
            telemetry.update();

            Motor1.setPower(gamepad1.left_stick_y);
            Motor2.setPower(gamepad1.left_stick_y);
            Motor3.setPower(gamepad1.right_stick_y );
            Motor4.setPower(gamepad1.right_stick_y);
            idle();
        }

    }
}
