package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.ControlBoard;

public interface Controllable {

	/**
	 * Gives the controller control of the robot
	 * 
	 * @param cb The ControlBoard object to operate the robot with
	 */
	public void controlWith(ControlBoard cb);
}
