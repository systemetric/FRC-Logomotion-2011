package org.usfirst.systemetric.util;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;

import edu.wpi.first.wpilibj.can.CANTimeoutException;
import static org.usfirst.systemetric.util.JaguarFactory.*;

public class OrthogonalMecanumDriveFactory {
	public static MecanumDrive createMecanumDrive(Vector size,
			double wheelRadius, double gearRatio) throws CANTimeoutException {

		double distancePerRotation = wheelRadius * 2 * Math.PI * gearRatio;
		Vector driveVector = new Vector(0, distancePerRotation);
		Vector offset = size.divideBy(2);

		final double SQRT_0_5 = Math.sqrt(0.5);

		return new MecanumDrive(new MecanumDrive.Wheel[] {
				new MecanumDrive.Wheel(new Vector(offset.x, offset.y),
						driveVector, new Vector(SQRT_0_5, SQRT_0_5),
						createSpeedController(2)),
				new MecanumDrive.Wheel(new Vector(offset.x, -offset.y),
						driveVector, new Vector(SQRT_0_5, -SQRT_0_5),
						createSpeedController(3)),
				new MecanumDrive.Wheel(new Vector(-offset.x, -offset.y),
						driveVector, new Vector(-SQRT_0_5, -SQRT_0_5),
						createSpeedController(4)),
				new MecanumDrive.Wheel(new Vector(-offset.x, offset.y),
						driveVector, new Vector(-SQRT_0_5, SQRT_0_5),
						createSpeedController(5)) });
	}
}
