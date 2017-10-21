package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Main Autonomous")
public class MainAutonomous extends LinearOpMode {

    // Declares robot object to get information from DriveMotors.java, Servos.java, Sensors.java
    DriveMotors         motors   = new DriveMotors();
    Servos              servos   = new Servos();
    Sensors             sensors  = new Sensors();

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

// Updates telemetry on phone to show it is initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

// Pulls hardware from robot object
        motors.init(hardwareMap);
        servos.init(hardwareMap);
        sensors.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

// Updates telemetry
            telemetry.update();

            if (sensors.color.red() > sensors.color.blue() && sensors.color.red() > sensors.color.green() && sensors.color.red() > 4) {
                // Insert code for if ball color is red
            } else if (sensors.color.blue() > sensors.color.red() && sensors.color.blue() > sensors.color.green() && sensors.color.blue() > 4) {
                // Insert code for if ball color is blue
            }

            idle();
        }
    }
}
