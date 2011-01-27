package org.usfirst.systemetric.robotics;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.parsing.IMechanism;

public class Grabber implements IMechanism {
	Solenoid tiltSolenoid;
	Solenoid grabSolenoid;

	public void close() {
		if (!grabSolenoid.get())
			grabSolenoid.set(true);
	}

	public void open() {
		if (grabSolenoid.get())
			grabSolenoid.set(false);
	}

	public void tiltUp() {
		if (!tiltSolenoid.get())
			tiltSolenoid.set(true);
	}

	public void tiltDown() {
		if (tiltSolenoid.get())
			tiltSolenoid.set(false);
	}
}
