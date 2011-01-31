package org.usfirst.systemetric;

import org.usfirst.systemetric.controllers.*;
import org.usfirst.systemetric.robotics.Arm;
import org.usfirst.systemetric.robotics.Grabber;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SmartDashboard;
import edu.wpi.first.wpilibj.SmartDashboardPacketFactory;
import edu.wpi.first.wpilibj.can.CANTimeoutException;


public class RobotTest extends IterativeRobot {
	GenericHID driveJoystick;
	GenericHID armJoystick;
	DriverStationEnhancedIO io;

	Arm arm;
	
	CANJaguar jag;
	// MecanumDrive mecanumDrive;

	Controllable grabberController = new GrabberController(new Grabber(1, 2));

	// Controllable driveController = new DriveController(mecanumDrive);

	public void teleopInit() {
		// driveJoystick = new Joystick(1);
		armJoystick = new Joystick(1);
		io = DriverStation.getInstance().getEnhancedIO();
		try {
			jag = new CANJaguar(2, ControlMode.kPercentVbus);
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	boolean ledState = false;

	public void teleopPeriodic() {
		grabberController.controlWith(armJoystick);
		// driveController.controlWith(driveJoystick);
		try {
			if (io.getDigital(1)) {
				ledState = !ledState;
				io.setLED(1, ledState);
				jag.setX(1);
			}
			else 
				jag.setX(-0.5);
				
		} catch (EnhancedIOException e) {
			e.printStackTrace();
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
}

