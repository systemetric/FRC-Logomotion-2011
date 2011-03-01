package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.controllers.Controller;
import org.usfirst.systemetric.controllers.GrabberController;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.Grabber;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.SimpleRobot;

/**
 * Tests the relay with the joystick. If moved more than halfway, relay is
 * turned on.
 * 
 * @author Eric
 * 
 */
public class RelayTest extends SimpleRobot {

	DriverStationLCD lcd               = DriverStationLCD.getInstance();
	GenericHID       driveJoystick     = new Joystick(1);

	Controller       grabberController = new GrabberController(new Grabber(0, 1));

	Relay            relay             = new Relay(2, Direction.kForward);

	public void operatorControl() {
		while (isOperatorControl()) {
			boolean trigger = new Vector(driveJoystick.getX(), driveJoystick.getY()).length() > 0.5;
			// driveJoystick.getTrigger();
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
