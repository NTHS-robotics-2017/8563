package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * Created by zain- on 9/30/2017.
 */

public class DriveMotors {

// Declaring motors
    public DcMotor  b_l  = null;
    public DcMotor  b_r   = null;

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
        b_l = hwMap.get(DcMotor.class, "m_b_l");
        b_r = hwMap.get(DcMotor.class, "m_b_r");
        b_r.setDirection(DcMotor.Direction.REVERSE);

// Sets motors to default to using encoders for movement, for driver operation have it set to not use them
        b_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        b_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
