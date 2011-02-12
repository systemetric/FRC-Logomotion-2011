package org.usfirst.systemetric.util;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive.Wheel;

import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class OrthogonalMecanumDriveFactory {

	static final double SQRT_0_5 = Math.sqrt(0.5);

	/**
	 * @param size
	 *            The rectangular size of the robot, using the center of each
	 *            wheel as the corners, in metres
	 * @param wheelRadius
	 *            The radius of the wheel, in metres
	 * @param gearRatio
	 *            The ratio of the motor shaft gear to the drive shaft gear
	 * @return A MecanumDrive, configured using speed control
	 * @throws RuntimeException
	 *             When the CAN Bus does not function
	 */
	public static MecanumDrive createMecanumDrive(Vector size,
	    double wheelRadius, double gearRatio) throws RuntimeException {

		double distancePerRotation = wheelRadius * 2 * Math.PI * gearRatio * 60;
		Vector rightDriveVector = new Vector(0, distancePerRotation);
		Vector leftDriveVector = new Vector(0, -distancePerRotation);
		Vector offset = size.divideBy(2);

		Wheel[] wheels = new Wheel[4];
		int motorCount = 0;
		try {

			wheels[motorCount] = new MecanumDrive.Wheel(new Vector(offset.x,
			    offset.y), rightDriveVector, new Vector(SQRT_0_5, SQRT_0_5),
			    JaguarFactory.createSpeedController(2));

			motorCount++;

			wheels[motorCount] = new MecanumDrive.Wheel(new Vector(offset.x,
			    -offset.y), rightDriveVector, new Vector(SQRT_0_5, -SQRT_0_5),
			    JaguarFactory.createSpeedController(3));

			motorCount++;

			wheels[motorCount] = new MecanumDrive.Wheel(new Vector(-offset.x,
			    -offset.y), leftDriveVector, new Vector(-SQRT_0_5, -SQRT_0_5),
			    JaguarFactory.createSpeedController(4));

			motorCount++;

			wheels[motorCount] = new MecanumDrive.Wheel(new Vector(-offset.x,
			    offset.y), leftDriveVector, new Vector(-SQRT_0_5, SQRT_0_5),
			    JaguarFactory.createSpeedController(5));

			motorCount++;

			return new MecanumDrive(wheels);

		} catch (CANTimeoutException e) {
			throw new RuntimeException("Could only connect " + motorCount
			    + " Jaguar(s)");
		}

	}

	/**
	 * @param size
	 *            The rectangular size of the robot, using the center of each
	 *            wheel as the corners
	 * @return A MecanumDrive, configured using percentage control
	 * @throws RuntimeException
	 *             When the CAN Bus does not function
	 */
	public static MecanumDrive createMecanumDrive(Vector size)
	    throws RuntimeException {

		Vector rightDriveVector = new Vector(0, 1);
		Vector leftDriveVector = new Vector(0, -1);

		// Get the distance from the centre of the robot to each wheel: half the
		// size
		Vector offset = size.divideBy(2);

		int motorCount = 0;
		try {
			Wheel[] wheels = new Wheel[4];

			wheels[motorCount] = new MecanumDrive.Wheel(new Vector(-offset.x,
			    offset.y), leftDriveVector, new Vector(-SQRT_0_5, SQRT_0_5),
			    JaguarFactory.createPercentageController(5));

			motorCount++;

			wheels[motorCount] = new Wheel(new Vector(offset.x, offset.y),
			    rightDriveVector, new Vector(SQRT_0_5, SQRT_0_5),
			    JaguarFactory.createPercentageController(2));

			motorCount++;

			wheels[motorCount] = new MecanumDrive.Wheel(new Vector(offset.x,
			    -offset.y), rightDriveVector, new Vector(SQRT_0_5, -SQRT_0_5),
			    JaguarFactory.createPercentageController(3));

			motorCount++;

			wheels[motorCount] = new MecanumDrive.Wheel(new Vector(-offset.x,
			    -offset.y), leftDriveVector, new Vector(-SQRT_0_5, -SQRT_0_5),
			    JaguarFactory.createPercentageController(4));

			motorCount++;

			return new MecanumDrive(wheels);

		} catch (CANTimeoutException e) {
			throw new RuntimeException("Could only connect " + motorCount
			    + " Jaguar(s)");
		}
	}
}
