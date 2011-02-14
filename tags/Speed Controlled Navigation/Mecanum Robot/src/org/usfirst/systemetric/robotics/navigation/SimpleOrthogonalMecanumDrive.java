package org.usfirst.systemetric.robotics.navigation;

import org.usfirst.systemetric.geometry.Matrix;
import org.usfirst.systemetric.geometry.Vector;

import edu.wpi.first.wpilibj.SpeedController;

public class SimpleOrthogonalMecanumDrive implements HolonomicDrive {
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
		Vector dir = Matrix.fromRotation(heading).times(direction);

		byte syncGroup = 0x02;

		frontRight.set(dir.y - dir.x, syncGroup);

	}

	public void setDriveVelocity(Vector driveVelocity) {
		// TODO Auto-generated method stub
		
	}

	public void setTurnVelocity(double turnVelocity) {
		// TODO Auto-generated method stub
		
	}

}
