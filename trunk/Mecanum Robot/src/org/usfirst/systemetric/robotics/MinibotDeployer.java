package org.usfirst.systemetric.robotics;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.parsing.IMechanism;

/**
 * Class for the minibot deployment
 * 
 * @author Louis
 * 
 */
public class MinibotDeployer implements IMechanism {
	Solenoid deploySolenoid;
	double   timePassed = 0;

	/**
	 * @param deployChannel
	 *            The channel of the solenoid controlling the downwards movement
	 *            of the deployment
	 */
	public MinibotDeployer(int deployChannel) {
		deploySolenoid = new Solenoid(deployChannel);
		retract();
	}

	public void retract() {
		deploySolenoid.set(false);
	}

	public void deploy() {
		/**
		 * Deploys the minibot
		 */
		deploySolenoid.set(true);
	}

	public boolean isDeployed() {
		/**
		 * returns the state of the minibot deployment mechanism
		 */
		return deploySolenoid.get();
	}

	public void updateTime(double currentTime) {
		timePassed = currentTime;
	}
}
