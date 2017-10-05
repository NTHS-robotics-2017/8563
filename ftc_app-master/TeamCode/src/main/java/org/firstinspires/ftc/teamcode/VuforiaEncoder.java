package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/*
 * Created by zain- on 10/1/2017.
 */

@Autonomous(name="VuforiaEncoder")
public class VuforiaEncoder extends LinearOpMode {
    
// Declares robot object to get information from DriveMotors.java
    DriveMotors     robot = new DriveMotors();

    private ElapsedTime     runtime = new ElapsedTime();

// Setting statistics of the motors/robot & doing basic math required in the program
    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 5 ;
    static final double     COUNTS         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double     DRIVE_SPEED             = .6;
    static final double     TURN_SPEED              = 0.5;

    public static final String TAG = "Vuforia Test";

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {

// Pulls hardware from robot object
        robot.init(hardwareMap);

// Updates telemetry to debug/make sure encoders reset
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

// Making sure encoders are at 0 position
//        robot.f_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.f_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.b_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.b_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        robot.f_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.f_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.b_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.b_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Telemetry debugging to find out if encoders didn't reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
//                robot.f_l.getCurrentPosition(),
//                robot.f_r.getCurrentPosition(),
                robot.b_l.getCurrentPosition(),
                robot.b_r.getCurrentPosition());
        telemetry.update();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ATsODcD/////AAAAAVw2lR...d45oGpdljdOh5LuFB9nDNfckoxb8COxKSFX";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        waitForStart();

        //Fetches the identity of the VuMark from the relic template
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        //If there is a visible VuMark in the camera, it will return either LEFT, CENTER, or RIGHT
        //This function makes it so it only runs everything after it if it actually detects a VuMark
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

            //If a VuMark is visible, it will tell you which column on the driver station.
            telemetry.addData("VuMark", "%s visible", vuMark);

            //If it's the left, this will run
            if (vuMark == RelicRecoveryVuMark.LEFT){
                telemetry.addLine("Success - L");
            }

            //If it's the center, this will run
            if (vuMark == RelicRecoveryVuMark.CENTER){
                telemetry.addLine("Success - C");
            }

            //If it's the right, this will run
            if (vuMark == RelicRecoveryVuMark.RIGHT){
                telemetry.addLine("Success - R");
            }
        }

// Reverse movement is entered as a negative value

// Drives 48 inches in a straight line, times out after 6 seconds.
        encoderDrive(DRIVE_SPEED, 48, 48, 6.0);
        sleep(2000);
        encoderDrive(TURN_SPEED, -4, 4, 5);
        sleep(2000);
        encoderDrive(DRIVE_SPEED, 48, 48, 5);
        sleep(2000);

// Turns right for 12 inches of wheel movement, then waits for 5 seconds
// encoderDrive(TURN_SPEED, 12, -12, 4.0);
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

// Creates an encoderDrive object to use in programs, having it do the math for you with a few basic inputs.
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
// Declaring Integers
//        int newfLeftTarget;
//        int newfRightTarget;
        int newbLeftTarget;
        int newbRightTarget;

// Loops as long as OpMode is running
        if (opModeIsActive()) {
// Uses math stated previously to calculate counts to move
//            newfLeftTarget = robot.f_l.getCurrentPosition() + (int)(leftInches * COUNTS);
//            newfRightTarget = robot.f_r.getCurrentPosition() + (int)(rightInches * COUNTS);
            newbLeftTarget = robot.b_l.getCurrentPosition() + (int)(leftInches * COUNTS);
            newbRightTarget = robot.b_r.getCurrentPosition() + (int)(rightInches * COUNTS);


// Sets encoder target position to new calculated position
//            robot.f_l.setTargetPosition(newfLeftTarget);
//            robot.f_r.setTargetPosition(newfRightTarget);
            robot.b_l.setTargetPosition(newbLeftTarget);
            robot.b_r.setTargetPosition(newbRightTarget);

// Runs the motors to get to the desired target position
//            robot.f_l.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.f_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.b_l.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.b_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();

// Sets the power of the motors to the absolute value of the speed
//            robot.f_l.setPower(Math.abs(speed));
//            robot.f_r.setPower(Math.abs(speed));
            robot.b_l.setPower(Math.abs(speed));
            robot.b_r.setPower(Math.abs(speed));

// Loops as long as OpMode is running
            while (opModeIsActive() &&

// Updates telemetry with new positions and current positions as long as the code and both motors are still running
                    (runtime.seconds() < timeoutS) &&
                    (/*robot.f_l.isBusy() && robot.f_r.isBusy()&& */robot.b_l.isBusy()&& robot.b_r.isBusy())) {

// Displays new target positions on the telemetry for debugging
                telemetry.addData("Path1",  "Running to %7d :%7d", /* newfLeftTarget,  newfRightTarget, */ newbLeftTarget,  newbRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
//                        robot.f_l.getCurrentPosition(),
//                        robot.f_r.getCurrentPosition(),
                        robot.b_l.getCurrentPosition(),
                        robot.b_r.getCurrentPosition());
                telemetry.update();
            }

// Sets the power of the motor to 0 to make sure there is no movement
//            robot.f_l.setPower(0);
//            robot.f_r.setPower(0);
            robot.b_l.setPower(0);
            robot.b_r.setPower(0);

//            robot.f_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.f_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.b_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.b_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
}
    