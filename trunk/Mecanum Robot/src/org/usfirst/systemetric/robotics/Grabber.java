package org.usfirst.systemetric.robotics;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.parsing.IMechanism;

/**
 * Class for the grabber on the end of the arm. Can be raised, lowered, opened,
 * and closed.
 * 
 * @author Eric
 * 
 */
public class Grabber implements IMechanism {
	Solenoid tiltSolenoid;
	Solenoid grabSolenoid;
	
	//State of the solenoids when grabbed and tilted up
	private final boolean TILTED_UP_STATE = false;
	private final boolean GRABBED_STATE = true;

	/**
	 * @param tiltChannel
	 *            The channel the solenoid controlling tilt is attached to
	 * @param grabChannel
	 *            The channel the solenoid controlling the claw is attached to
	 */
	public Grabber(int tiltChannel, int grabChannel) {
		tiltSolenoid = new Solenoid(tiltChannel);
		grabSolenoid = new Solenoid(grabChannel);
	}

	public void grab() {
		if (!isGrabbed())
			grabSolenoid.set(GRABBED_STATE);
	}

	public void release() {
		if (isGrabbed())
			grabSolenoid.set(!GRABBED_STATE);
	}

	public void tiltUp() {
		if (!isUp())
			tiltSolenoid.set(TILTED_UP_STATE);
	}

	public void tiltDown() {
		if (isUp())
			tiltSolenoid.set(!TILTED_UP_STATE);
	}

	public void toggle() {
		if (isGrabbed())
			release();
		else
			grab();
	}

	public void toggleTilt() {
		if (isUp())
			tiltDown();
		else
			tiltUp();
	}

	public boolean isUp() {
		return tiltSolenoid.get() == TILTED_UP_STATE;
	}

	public boolean isGrabbed() {
		return grabSolenoid.get() == GRABBED_STATE;
	}
}
