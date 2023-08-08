package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name = "AutoTest")
public class Auto extends LinearOpMode {

    //OpenCvInternalCamera phoneCam;
    OpenCvWebcam phoneCam;

    DuckDeterminationPipeline pipeline;

    ElapsedTime elapsedtimer = new ElapsedTime();

    public DcMotor lfmotor;
    public DcMotor lbmotor;
    public DcMotor rfmotor;
    public DcMotor rbmotor;

    public DcMotor lift;

    private Servo middleLift1;
    private Servo middleLift2;

//    private CRServo IntakeServoLeft;
//    private CRServo IntakeServoRight;



    static final double     COUNTS_PER_MOTOR_REV    = 537.7 ;// eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    String pos = "";

    @Override
    public void runOpMode()
    {


        lfmotor = hardwareMap.get(DcMotor.class,"lf");
        lbmotor = hardwareMap.get(DcMotor.class,"lb");
        rfmotor = hardwareMap.get(DcMotor.class,"rf");
        rbmotor = hardwareMap.get(DcMotor.class,"rb");

        lift = hardwareMap.get(DcMotor.class, "lift");

        middleLift1 = hardwareMap.get(Servo.class, "middle1");
        middleLift2 = hardwareMap.get(Servo.class, "middle2");





        //scoopa = hardwareMap.get(Servo.class, "scoopa");



        rfmotor.setDirection(DcMotor.Direction.REVERSE);
        lbmotor.setDirection(DcMotor.Direction.REVERSE);
//        rbmotor.setDirection(DcMotor.Direction.REVERSE);
        lfmotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();



        lfmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rfmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lbmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rbmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lfmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rfmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lbmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rbmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        telemetry.addData("Yay: ",  "Encoder reset done, ready to start");




        /**
         * NOTE: Many comments have been omitted from this sample for the
         * sake of conciseness. If you're just starting out with EasyOpenCv,
         * you should take a look at {@link InternalCamera1Example} or its
         * webcam counterpart, {@link WebcamExample} first.
         */

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        //phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        pipeline = new DuckDeterminationPipeline(telemetry);
        phoneCam.setPipeline(pipeline);

        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.setMillisecondsPermissionTimeout(2500);
        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                //phoneCam.startStreaming(320,240, OpenCvCameraRotation.UPSIDE_DOWN);
                phoneCam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        waitForStart();

        DuckDeterminationPipeline.DuckPosition position = pipeline.getAnalysis();
        telemetry.addData("Analysis", pipeline.getAnalysis());
        telemetry.update();

//        lift.setTargetPosition(lift.getCurrentPosition()-1000);
//        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift.setPower(0.5);


        //position.equals(DuckDeterminationPipeline.DuckPosition.RIGHT


        elapsedtimer.reset();


        middleLift1.setPosition(0.1);
        middleLift2.setPosition(0.1);


        wheelMove(-24,-24,-24,-24,0.5); //Initial move forward


        if(position.equals(DuckDeterminationPipeline.DuckPosition.ONE)){
            wheelMove(30,-30,-30,30,0.5);

        }
        else if(position.equals(DuckDeterminationPipeline.DuckPosition.TWO)){

        }
        else{
            wheelMove(-30,30,30,-30 ,0.5);
        }



    }
    public void wheelMove(int lfd, int lbd, int rfd, int rbd, double speed){
        lfmotor.setTargetPosition(lfmotor.getCurrentPosition()+((int)COUNTS_PER_INCH*lfd));
        lbmotor.setTargetPosition(lbmotor.getCurrentPosition()+((int)COUNTS_PER_INCH*lbd));
        rfmotor.setTargetPosition(rfmotor.getCurrentPosition()+((int)COUNTS_PER_INCH*rfd));
        rbmotor.setTargetPosition(rbmotor.getCurrentPosition()+((int)COUNTS_PER_INCH*rbd));

        lfmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lbmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rbmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rfmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lfmotor.setPower(speed);
        rfmotor.setPower(speed);
        lbmotor.setPower(speed);
        rbmotor.setPower(speed);

        while(lfmotor.isBusy() && lbmotor.isBusy() && rfmotor.isBusy() && rbmotor.isBusy()) {
            idle();
        }
    }

    public static class DuckDeterminationPipeline extends OpenCvPipeline
    {
        Telemetry telemetry;
        public DuckDeterminationPipeline(Telemetry telemetry){
            this.telemetry = telemetry;
        }

        public enum DuckPosition
        {
            ONE,
            TWO,
            THREE
        }

        /*
         * Some color constants
         */

        static final Scalar YELLOW = new Scalar(255, 255, 0);
        static final Scalar GREEN = new Scalar(0, 255, 0);
        static final Scalar RED = new Scalar(255,0,0);
        static final Scalar BLACK = new Scalar(0,0,0);
        static final Scalar WHITE = new Scalar(255,255,255);


        //88
        int threshold1 = 100;
        int threshold2 = 115;
        int threshold3 = 130;

        /*
         * The core values which define the location and size of the sample regions
         */
        static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(120,75);
        static final int REGION_WIDTH = 30;
        static final int REGION_HEIGHT = 45;

        /*
         * Points which actually define the sample region rectangles, derived from above values
         *
         * Example of how points A and B work to define a rectangle
         *
         *   ------------------------------------
         *   | (0,0) Point A                    |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                  Point B (70,50) |
         *   ------------------------------------
         *
         */
        Point region1_pointA = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x,
                REGION1_TOPLEFT_ANCHOR_POINT.y);
        Point region1_pointB = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        /*
         * Working variables
         */
        Mat region1_Cb;
        Mat YCrCb = new Mat();
        Mat Cb = new Mat();
        int avg1;

        // Volatile since accessed by OpMode thread w/o synchronization
        private volatile DuckPosition position = DuckPosition.ONE;
        /*
         * This function takes the RGB frame, converts to YCrCb,
         * and extracts the Cb channel to the 'Cb' variable
         */
        void inputToCb(Mat input)
        {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 2);
        }

        @Override
        public void init(Mat firstFrame)
        {
            /*
             * We need to call this in order to make sure the 'Cb'
             * object is initialized, so that the submats we make
             * will still be linked to it on subsequent frames. (If
             * the object were to only be initialized in processFrame,
             * then the submats would become delinked because the backing
             * buffer would be re-allocated the first time a real frame
             * was crunched)
             */
            inputToCb(firstFrame);

            /*
             * Submats are a persistent reference to a region of the parent
             * buffer. Any changes to the child affect the parent, and the
             * reverse also holds true.
             */
            region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
        }

        @Override
        public Mat processFrame(Mat input)
        {
            /*
             * Overview of what we're doing:
             *
             * We first convert to YCrCb color space, from RGB color space.
             * Why do we do this? Well, in the RGB color space, chroma and
             * luma are intertwined. In YCrCb, chroma and luma are separated.
             * YCrCb is a 3-channel color space, just like RGB. YCrCb's 3 channels
             * are Y, the luma channel (which essentially just a B&W image), the
             * Cr channel, which records the difference from red, and the Cb channel,
             * which records the difference from blue. Because chroma and luma are
             * not related in YCrCb, vision code written to look for certain values
             * in the Cr/Cb channels will not be severely affected by differing
             * light intensity, since that difference would most likely just be
             * reflected in the Y channel.
             *
             * After we've converted to YCrCb, we extract just the 2nd channel, the
             * Cb channel. We do this because stones are bright yellow and contrast
             * STRONGLY on the Cb channel against everything else, including SkyStones
             * (because SkyStones have a black label).
             *
             * We then take the average pixel value of 3 different regions on that Cb
             * channel, one positioned over each stone. The brightest of the 3 regions
             * is where we assume the SkyStone to be, since the normal stones show up
             * extremely darkly.
             *
             * We also draw rectangles on the screen showing where the sample regions
             * are, as well as drawing a solid rectangle over top the sample region
             * we believe is on top of the SkyStone.
             *
             * In order for this whole process to work correctly, each sample region
             * should be positioned in the center of each of the first 3 stones, and
             * be small enough such that only the stone is sampled, and not any of the
             * surroundings.
             */

            /*
             * Get the Cb channel of the input frame after conversion to YCrCb
             */
            inputToCb(input);

            /*
             * Compute the average pixel value of each submat region. We're
             * taking the average of a single channel buffer, so the value
             * we need is at index 0. We could have also taken the average
             * pixel value of the 3-channel image, and referenced the value
             * at index 2 here.
             */
            avg1 = (int) Core.mean(region1_Cb).val[0];

            telemetry.addData("Color Value: ",  avg1);
            telemetry.update();

            /*
             * Draw a rectangle showing sample region 1 on the screen.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    WHITE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines


            if(avg1<threshold1){
                position = DuckPosition.THREE; // Record our analysis
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region1_pointA, // First point which defines the rectangle
                        region1_pointB, // Second point which defines the rectangle
                        YELLOW, // The color the rectangle is drawn in
                        2); // Negative thickness means solid fill

            } else if (avg1<threshold2 && avg1>threshold1){
                position = DuckPosition.TWO;
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region1_pointA, // First point which defines the rectangle
                        region1_pointB, // Second point which defines the rectangle
                        RED, // The color the rectangle is drawn in
                        2); // Negative thickness means solid fill
            }
            else if (avg1>threshold2){
                position = DuckPosition.ONE;
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region1_pointA, // First point which defines the rectangle
                        region1_pointB, // Second point which defines the rectangle
                        BLACK, // The color the rectangle is drawn in
                        2); // Negative thickness means solid fill
            }






            /*
             * Render the 'input' buffer to the viewport. But note this is not
             * simply rendering the raw camera feed, because we called functions
             * to add some annotations to this buffer earlier up.
             */
            return input;
        }

        /*
         * Call this from the OpMode thread to obtain the latest analysis
         */
        public DuckPosition getAnalysis()
        {
            return position;
        }
    }
}
