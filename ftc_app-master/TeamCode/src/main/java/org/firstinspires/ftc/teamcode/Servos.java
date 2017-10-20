package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * Created by Chance- on 10/7/2017. For purposes.
 */

public class Servos {

    // Declaring motors
    public Servo  claw  = null;

    // Creating hwMap object and setting it to null
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    // Constructing Servos public object for use to call into another program
    public Servos() {

    }

    // This section runs during initialization
    public void init(HardwareMap ahwMap) {

// Giving hwMap a value
        hwMap = ahwMap;

// Declaring servos to use in other classes
        claw = hwMap.get(Servo.class, "servoClaw");
    }
}
