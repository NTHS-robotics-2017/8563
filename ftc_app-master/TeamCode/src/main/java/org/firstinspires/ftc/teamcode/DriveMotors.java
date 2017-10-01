package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * Created by zain- on 9/30/2017.
 */

public class DriveMotors
{
    public DcMotor  Motor1   = null;
    public DcMotor  Motor2  = null;
    public DcMotor  Motor3   = null;
    public DcMotor  Motor4   = null;

    HardwareMap hwMap           =  null;
    
    private ElapsedTime period  = new ElapsedTime();

      public DriveMotors(){

    }

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        Motor1 = hwMap.dcMotor.get("m_f_l");
        Motor2 = hwMap.dcMotor.get("m_b_l");
        Motor3 = hwMap.dcMotor.get("m_f_r");
        Motor4 = hwMap.dcMotor.get("m_b_r");
        Motor3.setDirection(DcMotor.Direction.REVERSE);
        Motor4.setDirection(DcMotor.Direction.REVERSE);


        Motor1.setPower(0);
        Motor2.setPower(0);
        Motor3.setPower(0);
        Motor4.setPower(0);

        Motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        /*Motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motor4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
    }
 }
