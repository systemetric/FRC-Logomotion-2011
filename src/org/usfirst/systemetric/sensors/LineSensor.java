package org.usfirst.systemetric.sensors;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.parsing.ISensor;

public class LineSensor extends DigitalInput implements ISensor {
	boolean invert;

	public LineSensor(int dataChannel) {
		this(dataChannel, false);
	}

	public LineSensor(int dataChannel, boolean invert) {
		super(dataChannel);
		this.invert = invert;
	}
	
	public boolean hasLine() {
		return get() != invert;
	}
}
