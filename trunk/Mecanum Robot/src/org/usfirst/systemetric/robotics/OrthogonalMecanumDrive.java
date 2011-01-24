package org.usfirst.systemetric.robotics;

import org.usfirst.systemetric.geometry.Matrix;
import org.usfirst.systemetric.geometry.Vector;

import edu.wpi.first.wpilibj.SpeedController;

public class OrthogonalMecanumDrive implements HolonomicDrive {
	SpeedController frontRight;
	SpeedController frontLeft;
	SpeedController backLeft;
	SpeedController backRight;

	Vector direction;
	double heading;

	Vector wheelbaseSize;

	public void setDirection(Vector direction) {
		this.direction = direction;
		update();
	}

	public void setOrientation(float heading) {
		this.heading = heading;
		update();
	}

	private void update() {
		Vector dir = Matrix.fromRotation(heading).multiply(direction);

		byte syncGroup = 0x02;

		frontRight.set(dir.y - dir.x, syncGroup);

	}

}
