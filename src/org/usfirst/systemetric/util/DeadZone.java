package org.usfirst.systemetric.util;

import org.usfirst.systemetric.geometry.Vector;

public class DeadZone {
	double deadFraction;
	public DeadZone(double deadFraction) {
		this.deadFraction = deadFraction;
    }
	
	public double applyTo(double input) {
		if (input > deadFraction) {
			return (input - deadFraction) / (1 - deadFraction);
		} else if (input < -deadFraction) {
			return (input + deadFraction) / (1 - deadFraction);
		} else {
			return 0;
		}
	}
	
	public Vector applyTo(Vector input) {
		double magnitude = input.length();

		if (magnitude < deadFraction) {
			return Vector.ZERO;
		} else {
			// The vector to subtract from the direction to account for
			// the dead zone
			Vector deadZoneOffset = input.unit().times(deadFraction);

			// Amount to multiply by to ensure that after subtraction, the
			// vector can still hit the maximum of (1,1)
			double multiplyFactor = magnitude / (1 - deadFraction);

			return input.minus(deadZoneOffset).times(multiplyFactor);
		}
	}
}
