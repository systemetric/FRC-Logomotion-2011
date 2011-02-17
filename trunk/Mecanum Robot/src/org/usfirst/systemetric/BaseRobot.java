package org.usfirst.systemetric;

import org.usfirst.systemetric.robotics.Arm;
import org.usfirst.systemetric.robotics.Grabber;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.sensors.LineTracer;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;


public class BaseRobot {
	Arm arm;
	Grabber grabber;
	MecanumDrive drive;
	Compressor compressor;
	
	LineTracer lineSensor;
	public BaseRobot() {
	    drive = OrthogonalMecanumDriveFactory.DEFAULT_ROBOT;
	    try {
	        arm = new Arm(6);
        } catch (CANTimeoutException e) {
	        throw new RuntimeException("The arm could not be connected");
        }
        
        //Create the grabber
        grabber = new Grabber(1, 2);

        //Create the grabber
        lineSensor = new LineTracer(new DigitalInput[] {}, 35); 
        
        /*TODO: wire up compressor*/
        compressor = new Compressor(0 , 0);
    }
}
