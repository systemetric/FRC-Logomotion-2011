package org.usfirst.systemetric;

import org.usfirst.systemetric.controllers.DriverStationIO;
import org.usfirst.systemetric.controllers.DriverStationIO.DigitalInput;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class ControlBoard {
    private static ControlBoard instance;
    
	private ControlBoard() throws EnhancedIOException {
	}
	
	public GenericHID driveJoystick = new Joystick(1);
	public GenericHID armJoystick = new Joystick(2);
	
	public DigitalInput minibitDeploy = new DigitalInput(1);
	
	public DigitalInput[] heightButtons = new DigitalInput[] {
			new DigitalInput(2),
			new DigitalInput(3),
			new DigitalInput(4),
			new DigitalInput(5),
			new DigitalInput(6),
			new DigitalInput(7),
			new DigitalInput(8)
	};
	
	public DigitalOutput grabberLED = new DigitalOutput(9);
	
	public static ControlBoard getInstance() throws EnhancedIOException {
		return instance == null ? new ControlBoard() : instance;
	}
}
