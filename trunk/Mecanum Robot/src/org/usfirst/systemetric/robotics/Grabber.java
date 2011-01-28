package org.usfirst.systemetric.robotics;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.parsing.IMechanism;

public class Grabber implements IMechanism {
	Solenoid tiltSolenoid;
	Solenoid grabSolenoid;

	public void close() {
		if (!isClosed())
			grabSolenoid.set(true);
	}

	public void open() {
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
			open();
		else
			close();
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
