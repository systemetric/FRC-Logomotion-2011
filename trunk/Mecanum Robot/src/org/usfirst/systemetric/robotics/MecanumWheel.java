/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.usfirst.systemetric.robotics;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.parsing.IMechanism;

import org.usfirst.systemetric.geometry.Matrix;
import org.usfirst.systemetric.geometry.Vector;

/**
 *
 * @author Eric
 */
public class MecanumWheel implements IMechanism{
	Vector position;
	Vector driveAxis;
	Vector rollAxis;
	
	Matrix transformMatrix;

	SpeedController motor;

	public MecanumWheel(Vector position, Vector driveAxis, Vector rollAxis, SpeedController motor)
	{
		this.position = position;
		this.driveAxis = driveAxis;
		this.rollAxis = rollAxis;

		this.motor = motor;

		transformMatrix = new Matrix(rollAxis,driveAxis);
		transformMatrix = transformMatrix.inverse();
	}

	public double getSpeed(Vector v)
	{
		Vector wheelVector = transformMatrix.multiply(v);
		return wheelVector.y;
	}

	public void setSpeed(double speed)
	{
		motor.set(speed);
	}
}
