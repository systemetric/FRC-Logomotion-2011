package org.usfirst.systemetric.utils;

import edu.wpi.first.wpilibj.PIDSource;

public class HeadingFinder implements PIDSource {
	AngleFinder angleFinder;
	double offset = 0;

	public HeadingFinder(AngleFinder angleFinder) {
		this.angleFinder = angleFinder;
	}

	public void reset() {
		double offset = angleFinder.getAngle();
		if (!Double.isNaN(offset))
			this.offset = offset;
	}

	public double getDegrees() {
		double angle = angleFinder.getAngle() - offset;

		while (angle < -180)
			angle += 360;
		while (angle >= 180)
			angle -= 360;

		return angle;
	}

	public double getRadians() {
		return Math.toRadians(getDegrees());
	}

	public double pidGet() {
		return getRadians();
	}

}
