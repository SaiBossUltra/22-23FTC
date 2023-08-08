package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="tELEOP")
public class Teleop extends LinearOpMode {

    public DcMotor lfmotor;
    public DcMotor lbmotor;
    public DcMotor rfmotor;
    public DcMotor rbmotor;

    public DcMotor lift;
    public DcMotor lift2;

    private Servo intake1;
    private Servo intake2;

    private Servo middleLift1;
    private Servo middleLift2;


    ElapsedTime elapsedtimer = new ElapsedTime();

    private int count = 1;

    private boolean isAPressesed = false;

    private int y = 1;

    private int counter = 0;




    @Override
    public void runOpMode() throws InterruptedException{


        lfmotor = hardwareMap.get(DcMotor.class,"lf");
        lbmotor = hardwareMap.get(DcMotor.class,"lb");
        rfmotor = hardwareMap.get(DcMotor.class,"rf");
        rbmotor = hardwareMap.get(DcMotor.class,"rb");

        lift = hardwareMap.get(DcMotor.class, "lift");
        lift2 = hardwareMap.get(DcMotor.class, "lift2");

        intake1 = hardwareMap.get(Servo.class, "claw1");
        intake2 = hardwareMap.get(Servo.class, "claw2");

        middleLift1 = hardwareMap.get(Servo.class, "middle1");
        middleLift2 = hardwareMap.get(Servo.class, "middle2");


        rfmotor.setDirection(DcMotor.Direction.REVERSE);
        lbmotor.setDirection(DcMotor.Direction.REVERSE);
//        rbmotor.setDirection(DcMotor.Direction.REVERSE);
        lfmotor.setDirection(DcMotor.Direction.REVERSE);

        middleLift1.setDirection(Servo.Direction.REVERSE);
        intake2.setDirection(Servo.Direction.REVERSE);


        lfmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lbmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rfmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rbmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        waitForStart();
        while(opModeIsActive()) {

            String x = "Bro you guys suck";
            telemetry.addData(">", x);
            telemetry.update();


            float move = gamepad1.left_stick_y * -1 ;
            float mecanum = gamepad1.left_stick_x;
            float turn = gamepad1.right_stick_x;

            double lfPower = move + mecanum + turn;
            double lbPower = move - mecanum + turn;
            double rfPower = move - mecanum - turn;
            double rbPower = move + mecanum - turn;


            //Making lift slower as it goes down to lessen risk of spool slipping
            double liftPower = 0.8*gamepad1.left_trigger - 0.8*gamepad1.right_trigger;


            if(gamepad2.right_bumper){
                intake1.setPosition(0.35);
                intake2.setPosition(0.35);
            }
            if(gamepad2.left_bumper){
                intake1.setPosition(0.75);
                intake2.setPosition(0.75);
            }


            lift.setPower(liftPower); //USE BRAKE FUNCTION TO HOLD LIFT UP
            lift2.setPower(liftPower);

            if(gamepad2.x){
                middleLift1.setPosition(0.1);
                middleLift2.setPosition(0.1);
            }
            if(gamepad2.y){
                middleLift1.setPosition(0.6);
                middleLift2.setPosition(0.6);
            }




            if(gamepad1.a){
                y = 0;
                counter = 1;
            }else if (gamepad1.b) {
                y = 1;
                counter = 0;
            }else if (gamepad1.x){
                y = 2;
            }

            if(y==0) {
                lfmotor.setPower(0.25*lfPower);
                lbmotor.setPower(0.25*lbPower);
                rfmotor.setPower(0.25*rfPower);
                rbmotor.setPower(0.25*rbPower);
            } else if(y==2){
                lfmotor.setPower(lfPower);
                lbmotor.setPower(lbPower);
                rfmotor.setPower(rfPower);
                rbmotor.setPower(rbPower);
            }
            else if (y==1){
                lfmotor.setPower(0.6*lfPower);
                lbmotor.setPower(0.6*lbPower);
                rfmotor.setPower(0.6*rfPower);
                rbmotor.setPower(0.6*rbPower);
            }



            elapsedtimer.reset();
//            if(gamepad1.right_bumper){
//                while(elapsedtimer.seconds() < 0.03){
//                    lfmotor.setPower(0.4);
//                    lbmotor.setPower(0.4);
//                    rfmotor.setPower(-0.4);
//                    rbmotor.setPower(-0.4);
//                }
//                lfmotor.setPower(0);
//                lbmotor.setPower(0);
//                rfmotor.setPower(0);
//                rbmotor.setPower(0);
//            }
//            if(gamepad1.left_bumper){
//                while(elapsedtimer.seconds() < 0.03){
//                    lfmotor.setPower(-0.4);
//                    lbmotor.setPower(-0.4);
//                    rfmotor.setPower(0.4);
//                    rbmotor.setPower(0.4);
//                }
//                lfmotor.setPower(0);
//                lbmotor.setPower(0);
//                rfmotor.setPower(0);
//                rbmotor.setPower(0);
//            }
        }
    }

//    private void setWheeldPower(float lf, float lb, float rf, float rb) {
//        rf.setPower(rf);
//        lf.setPower(rb);
//        lb.setPower(lf);
//        rb.setPower(lb);
//    }

}

