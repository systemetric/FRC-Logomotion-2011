/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.usfirst.systemetric.robotics.navigation;

import org.usfirst.systemetric.geometry.Matrix;
import org.usfirst.systemetric.geometry.Vector;

/**
 * 
 * @author Eric
 */
public class MecanumDrive implements HolonomicDrive {
	MecanumWheel[] wheels;
	final int numWheels;

	double speed = 1;

	public MecanumDrive(MecanumWheel[] wheels) {
		this.wheels = wheels;
		numWheels = wheels.length;
	}

	Vector driveVelocity;
	double turnVelocity;

	public void setDriveVelocity(Vector driveVelocity) {
		this.driveVelocity = driveVelocity;
		update();
	}

	public void setTurnVelocity(double turnVelocity) {
		this.turnVelocity = turnVelocity;
		update();
	}

	private void update() {
		double[] driveSpeeds = getDriveSpeeds();
		double[] turnSpeeds = getTurnSpeeds();
		
		for (int i = 0; i < numWheels; i++)
			driveSpeeds[i] = driveSpeeds[i] + turnSpeeds[i];
		
		//Make sure the the turn speed does not exceed the maximum of the motors
		driveSpeeds = normalize(driveSpeeds);

		for (int i = 0; i < numWheels; i++)
			wheels[i].setSpeed(driveSpeeds[i]);
	}

	/**
	 * Limits the speeds to a maximum of 1, keeping them proportional to each
	 * other
	 * 
	 * @param speeds
	 * @return normalized speeds
	 */
	private double[] normalize(double[] speeds, double maxSpeed) {
		maxSpeed = maxSpeed > 1 ? 1 : Math.abs(maxSpeed);

		double maxInputSpeed = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < numWheels; i++)
			maxInputSpeed = Math.max(Math.abs(speeds[i]), maxInputSpeed);

		for (int i = 0; i < numWheels; i++)
			speeds[i] = (speeds[i] / maxInputSpeed) * maxSpeed;

		return speeds;
	}
	
	private double[] normalize(double speeds[]) {
		return normalize(speeds, 1);
	}

	/**
	 * Get the Vector each wheel needs to move by in order to turn by the
	 * specified angle
	 * 
	 * @param angle
	 *            angle to turn by
	 * @return Array of Vectors representing wheel motion
	 */
	private double[] getTurnSpeeds() {
		double[] turnSpeeds = new double[numWheels];

		for (int i = 0; i < numWheels; i++) {
			Vector turnVector = Matrix.ROTATE90.multiply(wheels[i].position).scale(turnVelocity);
			turnSpeeds[i] = wheels[i].getSpeed(turnVector);
		}
		
		return turnSpeeds;
	}

	private double[] getDriveSpeeds() {
		double[] driveSpeeds = new double[numWheels];

		for (int i = 0; i < numWheels; i++)
			driveSpeeds[i] = wheels[i].getSpeed(driveVelocity);

		//Limit the speeds calculated for movement so that none are more than the maximum speed
		driveSpeeds = normalize(driveSpeeds, driveVelocity.length());
		return driveSpeeds;
	}
}
