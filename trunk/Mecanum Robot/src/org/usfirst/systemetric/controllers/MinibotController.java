package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.OperatorConsole;
import org.usfirst.systemetric.controllers.DriverStationIO.DigitalInput;
import org.usfirst.systemetric.robotics.Minibot;

import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.GenericHID;

/**
 * Class to control the minibot deployment
 * 
 * @author Louis
 * 
 */
public class MinibotController implements Controller {
	static final int    DEPLOY_TOGGLE_BUTTON       = 7;

	// TODO correct this value!!!
	static final double LENGTH_OF_ROUND_IN_SECONDS = 120;
	Minibot             minibot;
	double              timePassed                 = 0;

	public MinibotController(Minibot minibot) {
		this.minibot = minibot;
	}

	public void updateTime(double currentTime) {
		timePassed = currentTime;
		System.out.println(timePassed);
	}

	private double timePassedInSeconds() {
		return timePassed * 1000;
	}

	private boolean canWeDeployMinibot() {
		/**
		 * attempt to make sure the minibot cannot be deployed until 10 seconds
		 * before the end of the round
		 */

		return ((LENGTH_OF_ROUND_IN_SECONDS - timePassedInSeconds()) <= 10.0);
	}

	public void controlWith(OperatorConsole cb) {
		GenericHID joystick = cb.armJoystick;
		DigitalInput minibotButton = cb.minibotDeploy;

		// code with timer built in
		/*
		 * if (joystick.getRawButton(DEPLOY_TOGGLE_BUTTON) &&
		 * canWeDeployMinibot()) { System.out.println("minibot deployed");
		 * minibot.deploy(); }
		 */
		
		/*if (joystick.getRawButton(DEPLOY_TOGGLE_BUTTON)) {
			System.out.println("minibot deployed");
			minibot.deploy();
		}*/
		try {
	        if (minibotButton.get() || joystick.getRawButton(DEPLOY_TOGGLE_BUTTON)) {
	        	System.out.println("minibot deployed");
	        	minibot.deploy();
	        }
        } catch (EnhancedIOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
}
