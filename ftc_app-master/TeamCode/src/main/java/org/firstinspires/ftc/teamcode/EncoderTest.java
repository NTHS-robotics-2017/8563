package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * Created by zain- on 10/1/2017.
 */

@Autonomous(name="EncoderTest")

public class EncoderTest extends LinearOpMode {
    DcMotor Motor1 = null;
    DcMotor Motor2 = null;
    DcMotor Motor3 = null;
    DcMotor Motor4 = null;

    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;
    static final double     COUNTS         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double     DRIVE_SPEED             = .6;
//    static final double     TURN_SPEED              = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {

        Motor1 = hardwareMap.dcMotor.get("m_f_l");
        Motor2 = hardwareMap.dcMotor.get("m_b_l");
        Motor3 = hardwareMap.dcMotor.get("m_f_r");
        Motor4 = hardwareMap.dcMotor.get("m_b_r");
        Motor1.setDirection(DcMotor.Direction.REVERSE);
        Motor2.setDirection(DcMotor.Direction.REVERSE);

        Motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Motor4.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        Motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Path0", "Starting at %7d :%7d",
                Motor1.getCurrentPosition(),
                Motor3.getCurrentPosition());
        telemetry.update();

        waitForStart();

        encoderDrive(DRIVE_SPEED, 48, 48, 5.0);
        
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {

            newLeftTarget = Motor1.getCurrentPosition() + (int)(leftInches * COUNTS);
            newRightTarget = Motor3.getCurrentPosition() + (int)(rightInches * COUNTS);
            Motor1.setTargetPosition(newLeftTarget);
            Motor3.setTargetPosition(newRightTarget);

            Motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            Motor1.setPower(Math.abs(speed));
            Motor3.setPower(Math.abs(speed));


            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (Motor1.isBusy() && Motor3.isBusy())) {

                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        Motor1.getCurrentPosition(),
                        Motor3.getCurrentPosition());
                telemetry.update();
            }

            Motor1.setPower(0);
            Motor3.setPower(0);

            Motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
}

