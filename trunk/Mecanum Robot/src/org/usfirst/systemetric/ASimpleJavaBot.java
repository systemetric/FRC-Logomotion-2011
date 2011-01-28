package org.usfirst.systemetric;

import org.usfirst.systemetric.controllers.Controllable;
import org.usfirst.systemetric.controllers.GrabberController;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.BaseRobot;
import org.usfirst.systemetric.robotics.Grabber;
import org.usfirst.systemetric.robotics.navigation.MecanumWheel;

import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class ASimpleJavaBot extends SimpleRobot {
	/**
	 * This function is called once each time the robot enters autonomous mode.
	 */

	DriverStationLCD lcd = DriverStationLCD.getInstance();
	GenericHID driveJoystick = new Joystick(1);
	

	Controllable grabberController = new GrabberController(new Grabber(0,1));
	
	Relay relay = new Relay(2, Direction.kForward);

	public ASimpleJavaBot() {

	}

	public void autonomous() {
	}

	/**
	 * This function is called once each time the robot enters operator control.
	 */
	public void operatorControl() {
		lcd.println(Line.kUser2, 1, "Hello Java World V2!");
		lcd.updateLCD();
		while (isOperatorControl()) {
			boolean trigger = new Vector(driveJoystick.getX(), driveJoystick.getY()).length() > 0.5;//driveJoystick.getTrigger();
			lcd.println(Line.kUser3, 1, trigger ? "Pushed  " : "Released");
			
			lcd.updateLCD();
			relay.set(trigger ? Value.kOn : Value.kOff);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
