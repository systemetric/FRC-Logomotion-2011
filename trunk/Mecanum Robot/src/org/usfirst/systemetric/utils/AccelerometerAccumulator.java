package org.usfirst.systemetric.utils;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.sensors.ADXL345_I2C;

public class AccelerometerAccumulator {

	ADXL345_I2C accelerometer;
	long lastTime;
	Vector velocity;

	public AccelerometerAccumulator(ADXL345_I2C acc) {
		super();
		this.accelerometer = acc;
		reset();
	}

	public void update() {
		long currentTime = System.currentTimeMillis();

		long dt = currentTime - lastTime;
		lastTime = currentTime;
		velocity = velocity.add(accelerometer.getHorizontalAcceleration().times(
				dt / 1000.0));
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void reset() {
		velocity = new Vector(0, 0);
		lastTime = System.currentTimeMillis();
	}
}
