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
	    
	    //Create the arm
	    arm = new Arm(6);
        
        //Create the grabber
        grabber = new Grabber(2, 1);

        //Create the grabber
        lineSensor = new LineTracer(new DigitalInput[] {}, 35); 
        
        //Create the compressor
        compressor = new Compressor(1, 1);
    }
}
