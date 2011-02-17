package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.OperatorConsole;

public interface Controller {

	/**
	 * Gives the controller control of the robot
	 * 
	 * @param cb The ControlBoard object to operate the robot with
	 */
	public void controlWith(OperatorConsole cb);
}
