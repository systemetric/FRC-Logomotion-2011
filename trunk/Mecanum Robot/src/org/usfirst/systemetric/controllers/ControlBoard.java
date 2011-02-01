package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.controllers.DriverStationIO.DigitalInput;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class ControlBoard {
    private static ControlBoard instance;
    
	private ControlBoard() throws EnhancedIOException {
	}
	
	GenericHID driveJoystick = new Joystick(1);
	GenericHID armJoystick = new Joystick(2);
	
	DigitalInput minibitDeploy = new DigitalInput(1);
	
	DigitalInput[] heightButtons = new DigitalInput[] {
			new DigitalInput(2),
			new DigitalInput(3),
			new DigitalInput(4),
			new DigitalInput(5),
			new DigitalInput(6),
			new DigitalInput(7),
			new DigitalInput(8)
	};
	
	DigitalOutput grabberLED = new DigitalOutput(9);
	
	public static ControlBoard getInstance() throws EnhancedIOException {
		return instance == null ? new ControlBoard() : instance;
	}
}
