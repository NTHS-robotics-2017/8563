package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;

/*
 * Created by Chance- on 10/21/2017.
 */

public class Sensors {

// Declares sensor types
    public ModernRoboticsI2cColorSensor color;

    // Creating hwMap object and setting it to null
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    // Constructing Sensors public object for use to call into another program
    public Sensors() {

    }

    // This section runs during initialization
    public void init(HardwareMap ahwMap) {

// Giving hwMap a value
        hwMap = ahwMap;

// Declaring sensors to use in other classes
        color = hwMap.get(ModernRoboticsI2cColorSensor.class, "color");
    }
}
