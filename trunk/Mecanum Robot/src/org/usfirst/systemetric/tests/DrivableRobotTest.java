package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.OperatorConsole;
import org.usfirst.systemetric.controllers.ArmController;
import org.usfirst.systemetric.controllers.GrabberController;
import org.usfirst.systemetric.controllers.StrafeDriveController;
import org.usfirst.systemetric.robotics.Arm;
import org.usfirst.systemetric.robotics.Grabber;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;

public class DrivableRobotTest extends IterativeRobot {
	Compressor c = new Compressor(1, 1);	
	
	Grabber g = new Grabber(2, 1);
	GrabberController gc = new GrabberController(g);
	
	MecanumDrive m = OrthogonalMecanumDriveFactory.DEFAULT_ROBOT;
	StrafeDriveController mc = new StrafeDriveController(m);

	Arm a = new Arm(7);
	ArmController ac = new ArmController(a);
	
	public void teleopInit() {
	    c.start();
	}
	
	public void teleopPeriodic() {
		try {
			OperatorConsole ob = OperatorConsole.getInstance();
	        gc.controlWith(ob);
	        ac.controlWith(ob);
	        mc.controlWith(ob);
        } catch (EnhancedIOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
}
