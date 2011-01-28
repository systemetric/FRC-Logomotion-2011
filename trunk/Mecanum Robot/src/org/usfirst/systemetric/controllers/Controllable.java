package org.usfirst.systemetric.controllers;

import edu.wpi.first.wpilibj.GenericHID;

public interface Controllable {
	public void controlWith(GenericHID joystick);
}
