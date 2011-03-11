package org.usfirst.systemetric.sensors;

import edu.wpi.first.wpilibj.Solenoid;

public class LineSensor12V extends LineSensor {
	Solenoid powerSupply;

	public LineSensor12V(int dataChannel, int powerChannel) {
		this(dataChannel, powerChannel, false);
	}
	public LineSensor12V(int dataChannel, int powerChannel, boolean invert) {
		this(dataChannel, new Solenoid(powerChannel), invert);
	}
	public LineSensor12V(int dataChannel, Solenoid powerSupply) {
		this(dataChannel, powerSupply, false);
	}
	public LineSensor12V(int dataChannel, Solenoid powerSupply, boolean invert) {
		super(dataChannel, invert);
		this.powerSupply = powerSupply;
		enable();
	}
	
	public void enable() {
		powerSupply.set(true);
	}
	public void disable() {
		powerSupply.set(false);
	}
}
