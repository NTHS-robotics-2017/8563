package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * Created by zain- on 9/30/2017.
 */

public class DriveMotors
{
    //Declaring motors
    public DcMotor  f_l   = null;
    public DcMotor  b_l  = null;
    public DcMotor  f_r   = null;
    public DcMotor  b_r   = null;

    //Creating hwMap object and setting it to null
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    //Constructing DriveMotors public object for use to call into another program
    public DriveMotors() {

    }
    //This section runs during initialization
    public void init(HardwareMap ahwMap) {

        //Giving hwMap a value
        hwMap = ahwMap;

        //Declaring drive motors to use in other classes
        f_l = hwMap.get(DcMotor.class, "m_f_l");
        b_l = hwMap.get(DcMotor.class, "m_b_l");
        f_r = hwMap.get(DcMotor.class, "m_f_r");
        b_r = hwMap.get(DcMotor.class, "m_b_r");
        f_l.setDirection(DcMotor.Direction.REVERSE);
        b_l.setDirection(DcMotor.Direction.REVERSE);

/*        //Setting power to 0 to make sure nothing moves
        f_r.setPower(0);
        b_r.setPower(0);
        f_l.setPower(0);
        b_l.setPower(0);
*/
        //Sets motors to default to using encoders for movement, for driver operation have it set to not use them
        f_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        b_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        f_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        b_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
