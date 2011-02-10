package org.usfirst.systemetric.util;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;

import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class OrthogonalMecanumDriveFactory {
	
	static final double SQRT_0_5 = Math.sqrt(0.5);
	
	public static MecanumDrive createMecanumDrive(Vector size,
	    double wheelRadius, double gearRatio) throws CANTimeoutException {

		double distancePerRotation = wheelRadius * 2 * Math.PI * gearRatio;
		Vector rightDriveVector = new Vector(0, distancePerRotation);
		Vector leftDriveVector = new Vector(0, -distancePerRotation);
		Vector offset = size.divideBy(2);


		return new MecanumDrive(new MecanumDrive.Wheel[] {
		        new MecanumDrive.Wheel(new Vector(offset.x, offset.y),
		            leftDriveVector, new Vector(SQRT_0_5, SQRT_0_5),
		            JaguarFactory.createSpeedController(2)),
		        new MecanumDrive.Wheel(new Vector(offset.x, -offset.y),
		            leftDriveVector, new Vector(SQRT_0_5, -SQRT_0_5),
		            JaguarFactory.createSpeedController(3)),
		        new MecanumDrive.Wheel(new Vector(-offset.x, -offset.y),
		            rightDriveVector, new Vector(-SQRT_0_5, -SQRT_0_5),
		            JaguarFactory.createSpeedController(4)),
		        new MecanumDrive.Wheel(new Vector(-offset.x, offset.y),
		            rightDriveVector, new Vector(-SQRT_0_5, SQRT_0_5),
		            JaguarFactory.createSpeedController(5))});
	}
	
	public static MecanumDrive createMecanumDrive(Vector size) throws CANTimeoutException {

		Vector rightDriveVector = new Vector(0, 1);
		Vector leftDriveVector = new Vector(0, -1);
		Vector offset = size.divideBy(2);

		return new MecanumDrive(new MecanumDrive.Wheel[] {
		        new MecanumDrive.Wheel(new Vector(offset.x, offset.y),
		            leftDriveVector, new Vector(SQRT_0_5, SQRT_0_5),
		            JaguarFactory.createPercentageController(2)),
		        new MecanumDrive.Wheel(new Vector(offset.x, -offset.y),
		            leftDriveVector, new Vector(SQRT_0_5, -SQRT_0_5),
		            JaguarFactory.createPercentageController(3)),
		        new MecanumDrive.Wheel(new Vector(-offset.x, -offset.y),
		            rightDriveVector, new Vector(-SQRT_0_5, -SQRT_0_5),
		            JaguarFactory.createPercentageController(4)),
		        new MecanumDrive.Wheel(new Vector(-offset.x, offset.y),
		            rightDriveVector, new Vector(-SQRT_0_5, SQRT_0_5),
		            JaguarFactory.createPercentageController(5))});
	}
}
