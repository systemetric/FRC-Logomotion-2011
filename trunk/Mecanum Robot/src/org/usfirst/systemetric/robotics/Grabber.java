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
		if (!isClosed())
			grabSolenoid.set(true);
	}

	public void release() {
		if (isClosed())
			grabSolenoid.set(false);
	}

	public void tiltUp() {
		if (!isUp())
			tiltSolenoid.set(true);
	}

	public void tiltDown() {
		if (isUp())
			tiltSolenoid.set(false);
	}

	public void toggle() {
		if (isClosed())
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
		return tiltSolenoid.get();
	}

	public boolean isClosed() {
		return grabSolenoid.get();
	}
}
