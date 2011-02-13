package org.usfirst.systemetric.util;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive.Wheel;

import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class OrthogonalMecanumDriveFactory {

	static final double SQRT_0_5 = Math.sqrt(0.5);

	public static final MecanumDrive DEFAULT_ROBOT = createMecanumDrive(
			new Vector(0.55, 0.7), 0.075, 19.0 / 36.0);

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
		double wheelCircumference = wheelRadius * 2 * Math.PI;
		double distancePerRotation = wheelCircumference * gearRatio * 60;
		
		return createMecanumDrive(size, distancePerRotation,
				ControlMode.kPercentVbus);
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
		return createMecanumDrive(size, 1, ControlMode.kPercentVbus);
	}

	static MecanumDrive createMecanumDrive(Vector size,
			double distancePerRotation, ControlMode mode) {

		Vector rightDriveVector = new Vector(0, distancePerRotation);
		Vector leftDriveVector = new Vector(0, -distancePerRotation);
		Vector offset = size.divideBy(2);

		Wheel[] wheels = new Wheel[4];
		int motorCount = 0;
		try {

			wheels[motorCount] = new MecanumDrive.Wheel(new Vector(offset.x,
					offset.y), rightDriveVector,
					new Vector(SQRT_0_5, SQRT_0_5), JaguarFactory.createJaguar(
							2, mode));

			motorCount++;

			wheels[motorCount] = new MecanumDrive.Wheel(new Vector(offset.x,
					-offset.y), rightDriveVector, new Vector(SQRT_0_5,
					-SQRT_0_5), JaguarFactory.createJaguar(3, mode));

			motorCount++;

			wheels[motorCount] = new MecanumDrive.Wheel(new Vector(-offset.x,
					-offset.y), leftDriveVector, new Vector(-SQRT_0_5,
					-SQRT_0_5), JaguarFactory.createJaguar(4, mode));

			motorCount++;

			wheels[motorCount] = new MecanumDrive.Wheel(new Vector(-offset.x,
					offset.y), leftDriveVector,
					new Vector(-SQRT_0_5, SQRT_0_5),
					JaguarFactory.createJaguar(5, mode));

			motorCount++;

			return new MecanumDrive(wheels);

		} catch (CANTimeoutException e) {
			throw new RuntimeException("Could only connect " + motorCount
					+ " Jaguar(s)");
		}
	}
}
