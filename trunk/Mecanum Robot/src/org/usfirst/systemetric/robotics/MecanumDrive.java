/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.usfirst.systemetric.robotics;

import org.usfirst.systemetric.geometry.Vector;

/**
 *
 * @author Eric
 */
public class MecanumDrive implements HolonomicDrive {
	MecanumWheel[] wheels;
	final int numWheels;

	double speed = 1;

	public MecanumDrive(MecanumWheel[] wheels)
	{
		this.wheels = wheels;
		numWheels = wheels.length;
	}
	public void setDirection(Vector directionVector)
	{
		directionVector = directionVector.unit();
		double[] driveSpeeds = new double[numWheels];

		for(int i = 0; i< numWheels; i++)
			driveSpeeds[i] = wheels[i].getSpeed(directionVector);

		driveSpeeds = fixDriveSpeeds(driveSpeeds);


		for(int i = 0; i< numWheels; i++)
			wheels[i].setSpeed(driveSpeeds[i]);
	}

	private double[] fixDriveSpeeds(double[] speeds)
	{
		double maxSpeed = Double.NEGATIVE_INFINITY;

		for(int i = 0; i< numWheels; i++)
			maxSpeed = Math.max(Math.abs(speeds[i]), maxSpeed);

		for(int i = 0; i< numWheels; i++)
			speeds[i] = (speeds[i] / maxSpeed) * speed;

		return speeds;
	}

	public void setOrientation(float heading)
	{
	}
}
