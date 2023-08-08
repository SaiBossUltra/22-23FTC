package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="servoZero")
public class servoZero extends LinearOpMode {

    private Servo intake1;
    private Servo intake2;


    @Override
    public void runOpMode() throws InterruptedException {

        intake1 = hardwareMap.get(Servo.class, "middle1");
        intake2 = hardwareMap.get(Servo.class, "middle2");

        intake1.setDirection(Servo.Direction.REVERSE);



        waitForStart();
        while (opModeIsActive()) {

            String x = "right bumper = 0 position";
            telemetry.addData(">", x);
            telemetry.update();


            if(gamepad1.right_bumper){
                intake1.setPosition(0.05);
                intake2.setPosition(0.05);
            }
            if(gamepad1.left_bumper){
                intake1.setPosition(0.65);
                intake2.setPosition(0.65);
            }

        }
    }
}

