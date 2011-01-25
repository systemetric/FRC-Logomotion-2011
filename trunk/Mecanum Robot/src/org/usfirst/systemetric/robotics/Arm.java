package org.usfirst.systemetric.robotics;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.parsing.IMechanism;

public class Arm implements IMechanism {
	public static class PegPosition {
		final int BOTTOM = 1;
		final int BOTTOM_OFFSET = 2;
		final int MIDDLE = 3;
		final int MIDDLE_OFFSET = 4;
		final int TOP_OFFSET = 6;
	}
	
	Encoder encoder;
	SpeedController motor;
	
	public Arm(Encoder encoder, SpeedController motor) {
		super();
		this.encoder = encoder;
		this.motor = motor;
	}
	
	public void goToHeight(int encoderCount) {
		
	}
	
}
