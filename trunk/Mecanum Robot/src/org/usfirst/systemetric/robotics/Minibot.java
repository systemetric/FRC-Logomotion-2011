package org.usfirst.systemetric.robotics;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.parsing.IMechanism;

/**
 * Class for the minibot deployment
 * 
 * @author Louis
 * 
 */
public class Minibot implements IMechanism {
	Solenoid deploySolenoid;
	double   timePassed = 0;

	/**
	 * @param tiltChannel
	 *            The channel the solenoid controlling the downwards movement is
	 *            attached to (probably in slot 2)
	 */
	public Minibot(int deployChannel) {
		deploySolenoid = new Solenoid(deployChannel);
		deploySolenoid.set(false);
	}
	
	public void bringDeploymentUp (){
		deploySolenoid.set(false);
	}

	public void deploy() {
		/**
		 * Deploys the minibot
		 */
		deploySolenoid.set(true);
	}

	public boolean isDown() {
		/**
		 * returns the state of the minibot deployment mechanism
		 */
		return deploySolenoid.get();
	}

	public void updateTime(double currentTime) {
		timePassed = currentTime;
	}
}
