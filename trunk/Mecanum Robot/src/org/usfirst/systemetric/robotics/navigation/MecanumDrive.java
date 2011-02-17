/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.usfirst.systemetric.robotics.navigation;

import org.usfirst.systemetric.geometry.Matrix;
import org.usfirst.systemetric.geometry.Vector;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.parsing.IMechanism;

/**
 * A class for representing a generic Mecanum drive.
 * 
 * @author Eric
 */
public class MecanumDrive implements HolonomicDrive, PIDOutput {
	public static double MAX_SPEED = 1000;

	public static class Wheel implements IMechanism {
		Vector          position;
		public Matrix   transformMatrix;

		SpeedController motor;

		/**
		 * @param position
		 *            The position of the center of the mecanum wheel in respect
		 *            to the center of the robot
		 * @param driveAxis
		 *            The direction which the wheel moves when powered, if the
		 *            rollers were glued solid
		 * @param rollAxis
		 *            The direction which the wheels roll freely, if the motor
		 *            shaft is held still
		 * @param motor
		 *            The SpeedController object representing the actual motor
		 */
		public Wheel(Vector position, Vector driveAxis, Vector rollAxis, SpeedController motor) {
			this.position = position;

			this.motor = motor;

			transformMatrix = new Matrix(rollAxis, driveAxis);
		}

		public double getSpeed(Vector v) {
			Vector wheelVector = transformMatrix.inverse().times(v);
			return wheelVector.y;
		}
	}

	Wheel[]   wheels;
	final int numWheels;

	double    speed = 1;

	/**
	 * Construct a MecanumDrive from the specified set of wheels
	 * 
	 * @param wheels
	 */
	public MecanumDrive(Wheel[] wheels) {
		this.wheels = wheels;
		numWheels = wheels.length;
	}

	Vector driveVelocity = new Vector(0, 0);
	double turnVelocity;

	public void setDriveVelocity(Vector driveVelocity) {
		this.driveVelocity = driveVelocity;
		update();
	}

	public void setTurnVelocity(double turnVelocity) {
		this.turnVelocity = turnVelocity;
		update();
	}

	public void set(Vector driveVelocity, double turnVelocity) {
		this.driveVelocity = driveVelocity;
		this.turnVelocity = turnVelocity;
		update();
	}

	protected void update() {
		final byte syncGroup = 0x02;

		double[] driveSpeeds = getDriveSpeeds();
		double[] turnSpeeds = getTurnSpeeds();

		for (int i = 0; i < numWheels; i++)
			driveSpeeds[i] = driveSpeeds[i] + turnSpeeds[i];

		// Make sure the the turn speed does not exceed the maximum of the
		// motors
		driveSpeeds = normalize(driveSpeeds);

		for (int i = 0; i < numWheels; i++)
			wheels[i].motor.set(driveSpeeds[i], syncGroup);

		try {
			CANJaguar.updateSyncGroup(syncGroup);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Limits the speeds to a maximum of 1, keeping them proportional to each
	 * other
	 * 
	 * @param speeds
	 * @return normalized speeds
	 */
	protected double[] normalize(double[] speeds, double maxSpeed, boolean scale) {

		maxSpeed = maxSpeed > MAX_SPEED ? MAX_SPEED : Math.abs(maxSpeed);

		// Find the maximum speed in the speeds[] array
		double maxInputSpeed = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < numWheels; i++)
			maxInputSpeed = Math.max(Math.abs(speeds[i]), maxInputSpeed);

		if (scale || maxInputSpeed > maxSpeed)
			for (int i = 0; i < numWheels; i++)
				speeds[i] = (speeds[i] / maxInputSpeed) * maxSpeed;

		return speeds;
	}

	protected double[] normalize(double speeds[]) {
		return normalize(speeds, MAX_SPEED, false);
	}

	/**
	 * Get the Vector each wheel needs to move by in order to turn by the
	 * specified angle
	 * 
	 * @param angle
	 *            angle to turn by
	 * @return Array of Vectors representing wheel motion
	 */
	protected double[] getTurnSpeeds() {
		double[] turnSpeeds = new double[numWheels];

		for (int i = 0; i < numWheels; i++) {
			Vector turnVector = Matrix.ROTATE90.times(wheels[i].position).times(turnVelocity);
			turnSpeeds[i] = wheels[i].getSpeed(turnVector);
		}

		return turnSpeeds;
	}

	protected double[] getDriveSpeeds() {
		double[] driveSpeeds = new double[numWheels];

		for (int i = 0; i < numWheels; i++)
			driveSpeeds[i] = wheels[i].getSpeed(driveVelocity);

		// Limit the speeds calculated for movement so that none are more than
		// the maximum speed
		// driveSpeeds = normalize(driveSpeeds, driveVelocity.length());
		return driveSpeeds;
	}

	public void pidWrite(double output) {
		setTurnVelocity(output);
	}
}
