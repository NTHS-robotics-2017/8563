package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * Created by zain- on 9/30/2017.
 */

public class DriveMotors {

// Declaring motors
    public DcMotor  left  = null;
    public DcMotor  right  = null;
    public DcMotor  arm  = null;

// Creating hwMap object and setting it to null
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

// Constructing DriveMotors public object for use to call into another program
    public DriveMotors() {

    }

// This section runs during initialization
    public void init(HardwareMap ahwMap) {

// Giving hwMap a value
        hwMap = ahwMap;

// Declaring drive motors to use in other classes
        left = hwMap.get(DcMotor.class, "m_l");
        right = hwMap.get(DcMotor.class, "m_r");
        arm = hwMap.get(DcMotor.class, "arm");

// Sets right motor to reverse direction
        right.setDirection(DcMotor.Direction.REVERSE);

// Sets motors to default to using encoders for movement, for driver operation have it set to not use them
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
