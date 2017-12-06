package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Motors;
import org.firstinspires.ftc.teamcode.Hardware.Sensors;
import org.firstinspires.ftc.teamcode.Hardware.Servos;

@Autonomous(name="Auto Red Team")
public class AutoRedTeam extends LinearOpMode {

// Declares robot object to get information from DriveMotors.java, Servos.java, Sensors.java
    Motors motors   = new Motors();
    Servos servos   = new Servos();
    Sensors sensors  = new Sensors();
// Imports Drive by Gyro program
    DriveByGyroConcept Gyro = new DriveByGyroConcept();

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        double DRIVE_SPEED = .6;

// Updates telemetry on phone to show it is initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

// Pulls hardware from robot object
        motors.init(hardwareMap);
        servos.init(hardwareMap);
        sensors.init(hardwareMap);

        Gyro.init();

        waitForStart();

        while (opModeIsActive()) {

// Updates telemetry
            telemetry.update();

            /*if (sensors.color.red() > sensors.color.blue() && sensors.color.red() > sensors.color.green() && sensors.color.red() > 4) {
                // Insert code for if ball color is red
            } else if (sensors.color.blue() > sensors.color.red() && sensors.color.blue() > sensors.color.green() && sensors.color.blue() > 4) {
                // Insert code for if ball color is blue
            }
          */  Gyro.gyroDrive(DRIVE_SPEED,38,0);
            idle();
        }
    }
}
