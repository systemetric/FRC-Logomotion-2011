package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.robotics.Grabber;

import edu.wpi.first.wpilibj.GenericHID;

public class GrabberController implements Controllable{
	Grabber grabber;

	boolean toggled = false;
	boolean tiltToggled = false;

	public GrabberController(Grabber grabber) {
		this.grabber = grabber;
	}

	public void controlWith(GenericHID joystick) {
		if (joystick.getTrigger() && !toggled) {
			grabber.toggle();
			toggled = true;
		} else
			toggled = false;

		if (joystick.getRawButton(2) && !tiltToggled) {
			grabber.toggleTilt();
			tiltToggled = true;
		} else
			tiltToggled = false;
	}

}
