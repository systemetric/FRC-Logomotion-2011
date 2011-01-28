package org.usfirst.systemetric;

import org.usfirst.systemetric.controllers.*;
import org.usfirst.systemetric.robotics.Arm;
import org.usfirst.systemetric.robotics.Grabber;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class RobotTest extends IterativeRobot {
	GenericHID driveJoystick;
	GenericHID armJoystick;

	Arm arm;
	MecanumDrive mecanumDrive;
	
	Controllable grabberController = new GrabberController(new Grabber());
	Controllable driveController = new DriveController(mecanumDrive);


	public void autonomousInit() {
		driveJoystick = new Joystick(1);
		armJoystick = new Joystick(2);
	}

	public void autonomousPeriodic() {
		grabberController.controlWith(armJoystick);
		driveController.controlWith(driveJoystick);
	};
}
