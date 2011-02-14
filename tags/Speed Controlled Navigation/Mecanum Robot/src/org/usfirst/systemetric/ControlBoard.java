package org.usfirst.systemetric;

import java.util.TimerTask;

import org.usfirst.systemetric.controllers.DriverStationIO;
import org.usfirst.systemetric.controllers.DriverStationIO.DigitalInput;
import org.usfirst.systemetric.controllers.DriverStationIO.DigitalOutput;

import com.sun.squawk.util.Arrays;

import edu.wpi.first.testing.Failure;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Watchdog;

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
	
	public Timer teleopTimer;
	public java.util.Timer t2;
	public TimerTask t;
	
	public static ControlBoard getInstance() throws EnhancedIOException {
		return instance == null ? new ControlBoard() : instance;
		
	}
}
