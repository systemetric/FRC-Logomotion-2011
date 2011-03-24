package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.BaseRobot;
import org.usfirst.systemetric.OperatorConsole;
import org.usfirst.systemetric.controllers.StrafeDriveController;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * Basic drive code for testing whether the Mecanum code works in real life.
 * Also tests the two arm Jaguars
 * 
 * @author Eric
 * 
 */
public class MecanumTest extends IterativeRobot {
	MecanumDrive robot;
	StrafeDriveController driveController;

	public void robotInit() {
		Timer.delay(2);

		driveController = new StrafeDriveController(BaseRobot.getInstance());
	}

	public void teleopPeriodic() {		
		try {
	        driveController.controlWith(OperatorConsole.getInstance());
        } catch (EnhancedIOException e) {
	        e.printStackTrace();
        }
	}
}
