package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.robotics.Grabber;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SmartDashboard;

public class GrabberController implements Controllable {
	static final int TILT_TOGGLE_BUTTON = 3;
	static final int GRAB_TOGGLE_BUTTON = 1; //Trigger
	Grabber grabber;

	boolean toggled = false;
	boolean tiltToggled = false;

	public GrabberController(Grabber grabber) {
		this.grabber = grabber;
	}

	public void controlWith(ControlBoard cb) {
		GenericHID joystick = cb.armJoystick;
		if (!joystick.getRawButton(GRAB_TOGGLE_BUTTON)) {
			toggled = false;
		} else if (!toggled) {
			grabber.toggle();
			toggled = true;
		}
		SmartDashboard.log(grabber.isClosed() ? "Closed" : "Open", "Grabber state");

		if (!joystick.getRawButton(TILT_TOGGLE_BUTTON))
			tiltToggled = false;
		else if (!tiltToggled) {
			grabber.toggleTilt();
			tiltToggled = true;
		}
		SmartDashboard.log(grabber.isUp()/* ? "Up" : "Down"*/, "Tilt state");
	}
}
