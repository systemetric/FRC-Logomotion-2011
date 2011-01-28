package org.usfirst.systemetric;

import org.usfirst.systemetric.controllers.*;
import org.usfirst.systemetric.robotics.Arm;
import org.usfirst.systemetric.robotics.Grabber;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SmartDashboard;
import edu.wpi.first.wpilibj.SmartDashboardPacketFactory;

public class RobotTest extends IterativeRobot {
	GenericHID driveJoystick;
	GenericHID armJoystick;

	Arm arm;
	//MecanumDrive mecanumDrive;
	
	Controllable grabberController = new GrabberController(new Grabber(1, 2));
	//Controllable driveController = new DriveController(mecanumDrive);


	public void teleopInit() {
		//driveJoystick = new Joystick(1);
		armJoystick = new Joystick(1);
	}

	public void teleopPeriodic() {
		grabberController.controlWith(armJoystick);
		//driveController.controlWith(driveJoystick);
	};
}
