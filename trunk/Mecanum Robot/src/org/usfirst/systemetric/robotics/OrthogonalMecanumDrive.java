package org.usfirst.systemetric.robotics;

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
	
	
	
	public void setDirection(Vector d) {
		byte syncGroup = 0x02;
		
		frontRight.set(d.y - d.x, syncGroup);
		frontRight.set(d.y - d.x, syncGroup);

	}

	public void setOrientation(float heading) {
		// TODO Auto-generated method stub

	}
	
	private void update() {
		
	}

}
