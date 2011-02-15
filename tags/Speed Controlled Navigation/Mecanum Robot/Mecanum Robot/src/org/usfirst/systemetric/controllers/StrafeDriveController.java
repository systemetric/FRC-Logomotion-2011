package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.OperatorConsole;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.VectorSmoother;

import edu.wpi.first.wpilibj.GenericHID;

public class StrafeDriveController implements Controllable {
	/** The speed to run the robot at when the trigger is pressed */
	public static final double CRAWL_FACTOR  = 0.5;

	/**
	 * Radius of the circular zone in the middle of the Joystick in which no
	 * speed is set
	 */

	public static final double DEAD_ZONE     = 0.05;

	/**
	 * The higher this value the faster the acceleration. Between 1 and 0
	 */
	public static final double SMOOTH_FACTOR = 1;//0.2;

	MecanumDrive               drive;
	VectorSmoother             smoother;

	public StrafeDriveController(MecanumDrive drive) {
		this.drive = drive;
		smoother = new VectorSmoother(SMOOTH_FACTOR);
	}

	private Vector addDeadZone(Vector vector) {
		double magnitude = vector.length();

		if (magnitude < DEAD_ZONE) {
			return Vector.ZERO;
		} else {
			// The vector to subtract from the drive direction to account for
			// the dead zone
			Vector deadZoneOffset = vector.unit().times(DEAD_ZONE);

			// Amount to multiply by to ensure that after subtraction, the
			// vector can still hit the maximum of (1,1)
			double multiplyFactor = magnitude / (1 - DEAD_ZONE);

			return vector.minus(deadZoneOffset).times(multiplyFactor);
		}
	}

	public void controlWith(OperatorConsole cb) {
		GenericHID joystick = cb.driveJoystick;

		// Get information from joystick
		Vector driveVector = new Vector(joystick.getX(), joystick.getY());
		//double turnSpeed = joystick.getTwist();
		
		boolean left = joystick.getRawButton(4);
		boolean right = joystick.getRawButton(5);
		
		double turnSpeed = left ? -1 : right ? 1 : 0;

		driveVector = addDeadZone(driveVector);

		// If trigger is pressed, use fine control
		if (joystick.getTrigger())
			driveVector = driveVector.times(CRAWL_FACTOR);

		driveVector = smoother.smooth(driveVector);

		drive.set(driveVector, turnSpeed);
	}

}
