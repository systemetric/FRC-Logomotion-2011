package org.usfirst.systemetric;

import java.util.TimerTask;

import org.usfirst.systemetric.controllers.DriverStationIO.DigitalInput;
import org.usfirst.systemetric.controllers.DriverStationIO.DigitalOutput;

import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class OperatorConsole {
	private static OperatorConsole instance;

	private OperatorConsole() throws EnhancedIOException {
	}

	public GenericHID      driveJoystick = new Joystick(1);
	public GenericHID      armJoystick   = new Joystick(2);

	public DigitalInput    minibotDeploy = new DigitalInput(2);

	/*
	 * //some random tests for LEDs public DigitalOutput[] minibotLEDs = new
	 * DigitalOutput [] { new DigitalOutput(3), new DigitalOutput(5), new
	 * DigitalOutput(7), new DigitalOutput(5)} }
	 */

	public DigitalOutput   grabberLED    = new DigitalOutput(9);
	public DigitalOutput   grabberLED2   = new DigitalOutput(8);
	public DigitalOutput   grabberLED3   = new DigitalOutput(7);
	public DigitalOutput   grabberLED4   = new DigitalOutput(6);

	public Timer           teleopTimer;
	public java.util.Timer t2;
	public TimerTask       t;

	public static OperatorConsole getInstance() throws EnhancedIOException {
		return instance == null ? new OperatorConsole() : instance;

	}
}
