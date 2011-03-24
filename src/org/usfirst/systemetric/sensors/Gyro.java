package org.usfirst.systemetric.sensors;

import org.usfirst.systemetric.util.AngleFinder;

import edu.wpi.first.wpilibj.AnalogChannel;

public class Gyro extends edu.wpi.first.wpilibj.Gyro implements AngleFinder {

	public Gyro(AnalogChannel channel) {
		super(channel);
	}

	public Gyro(int slot, int channel) {
		super(slot, channel);
	}

	public Gyro(int channel) {
		super(channel);
	}

}
