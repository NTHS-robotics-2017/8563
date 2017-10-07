package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/*
 * Created by zain- on 10/1/2017.
 */

@Autonomous(name="Drive By Gyro")
@Disabled
public class DriveByGyroConcept extends LinearOpMode {

    DriveMotors         robot   = new DriveMotors();
    ModernRoboticsI2cGyro   gyro    = null;

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);


    static final double     DRIVE_SPEED             = 0.7;
    static final double     TURN_SPEED              = 0.5;

    static final double     HEADING_THRESHOLD       = 1 ;
    static final double     P_TURN_COEFF            = 0.1;
    static final double     P_DRIVE_COEFF           = 0.15;


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

        robot.b_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.b_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData(">", "Calibrating Gyro");    //
        telemetry.update();

        gyro.calibrate();

        while (!isStopRequested() && gyro.isCalibrating())  {
            sleep(50);
            idle();
        }

        telemetry.addData(">", "Robot Ready.");    //
        telemetry.update();

        robot.b_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.b_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (!isStarted()) {
            telemetry.addData(">", "Robot Heading = %d", gyro.getIntegratedZValue());
            telemetry.update();
        }

        gyro.resetZAxisIntegrator();

        gyroDrive(DRIVE_SPEED, 48.0, 0.0);    // Drive FWD 48 inches
        gyroTurn( TURN_SPEED, -45.0);         // Turn  CCW to -45 Degrees
        gyroHold( TURN_SPEED, -45.0, 0.5);    // Hold -45 Deg heading for a 1/2 second
        gyroDrive(DRIVE_SPEED, 12.0, -45.0);  // Drive FWD 12 inches at 45 degrees
        gyroTurn( TURN_SPEED,  45.0);         // Turn  CW  to  45 Degrees
        gyroHold( TURN_SPEED,  45.0, 0.5);    // Hold  45 Deg heading for a 1/2 second
        gyroTurn( TURN_SPEED,   0.0);         // Turn  CW  to   0 Degrees
        gyroHold( TURN_SPEED,   0.0, 1.0);    // Hold  0 Deg heading for a 1 second
        gyroDrive(DRIVE_SPEED,-48.0, 0.0);    // Drive REV 48 inches

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    public void gyroDrive ( double speed,
                            double distance,
                            double angle) {

        int     newLeftTarget;
        int     newRightTarget;
        int     moveCounts;
        double  max;
        double  error;
        double  steer;
        double  leftSpeed;
        double  rightSpeed;

        if (opModeIsActive()) {

            moveCounts = (int)(distance * COUNTS_PER_INCH);
            newLeftTarget = robot.b_l.getCurrentPosition() + moveCounts;
            newRightTarget = robot.b_r.getCurrentPosition() + moveCounts;

            robot.b_l.setTargetPosition(newLeftTarget);
            robot.b_r.setTargetPosition(newRightTarget);

            robot.b_l.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.b_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
            robot.b_l.setPower(speed);
            robot.b_r.setPower(speed);

            while (opModeIsActive() &&
                    (robot.b_l.isBusy() && robot.b_r.isBusy())) {

                error = getError(angle);
                steer = getSteer(error, P_DRIVE_COEFF);

                if (distance < 0)
                    steer *= -1.0;

                leftSpeed = speed - steer;
                rightSpeed = speed + steer;

                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                if (max > 1.0)
                {
                    leftSpeed /= max;
                    rightSpeed /= max;
                }

                robot.b_l.setPower(leftSpeed);
                robot.b_r.setPower(rightSpeed);

                telemetry.addData("Err/St",  "%5.1f/%5.1f",  error, steer);
                telemetry.addData("Target",  "%7d:%7d",      newLeftTarget,  newRightTarget);

                telemetry.addData("Actual",  "%7d:%7d", robot.b_l.getCurrentPosition(),
                        robot.b_r.getCurrentPosition());

                telemetry.addData("Speed",   "%5.2f:%5.2f",  leftSpeed, rightSpeed);
                telemetry.update();
            }

            robot.b_l.setPower(0);
            robot.b_r.setPower(0);

            robot.b_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.b_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }


    public void gyroTurn (  double speed, double angle) {

        while (opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF)) {
            telemetry.update();
        }
    }

    public void gyroHold( double speed, double angle, double holdTime) {

        ElapsedTime holdTimer = new ElapsedTime();

        holdTimer.reset();
        while (opModeIsActive() && (holdTimer.time() < holdTime)) {
            onHeading(speed, angle, P_TURN_COEFF);
            telemetry.update();
        }

        robot.b_l.setPower(0);
        robot.b_r.setPower(0);
    }

    boolean onHeading(double speed, double angle, double PCoeff) {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double leftSpeed;
        double rightSpeed;

        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, PCoeff);
            rightSpeed  = speed * steer;
            leftSpeed   = -rightSpeed;
        }

        robot.b_l.setPower(leftSpeed);
        robot.b_r.setPower(rightSpeed);

        telemetry.addData("Target", "%5.2f", angle);
        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);

        return onTarget;
    }

    public double getError(double targetAngle) {

        double robotError;

// Calculate error in -179 to +180 range  (
        robotError = targetAngle - gyro.getIntegratedZValue();
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }
}
