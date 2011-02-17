package org.usfirst.systemetric;

import org.usfirst.systemetric.robotics.Arm;
import org.usfirst.systemetric.robotics.Grabber;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.can.CANTimeoutException;


public class InsertRobotNameHere {
	Arm arm;
	Grabber grabber;
	MecanumDrive drive;
	Compressor compressor;
	public InsertRobotNameHere() {
	    drive = OrthogonalMecanumDriveFactory.DEFAULT_ROBOT;
	    try {
	        arm = new Arm(6);
        } catch (CANTimeoutException e) {
	        throw new RuntimeException("The arm could not be connected");
        }
        
        grabber = new Grabber(1, 2);
    }
}
