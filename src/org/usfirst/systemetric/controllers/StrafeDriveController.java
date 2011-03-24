package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.BaseRobot;
import org.usfirst.systemetric.OperatorConsole;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.DeadZone;
import org.usfirst.systemetric.util.VectorSmoother;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class StrafeDriveController implements Controller {
	/** The speed to run the robot at when the trigger is pressed */
	public static final double CRAWL_FACTOR = 0.5;
	public static final double ZOOM_FACTOR = 1.5;

	/** The higher this value the faster the acceleration. Between 1 and 0 */
	public static final double SMOOTH_FACTOR = 0.1;

	/**
	 * Radius of the circular zone in the middle of the {@link Joystick} in
	 * which no speed is set
	 */

	public static final DeadZone deadZone = new DeadZone(0.05);

	BaseRobot robot;
	VectorSmoother smoother;
	double speed;
	/** @deprecated */
	public StrafeDriveController(BaseRobot robot) {
		this(robot, 1);
	}
	
	public StrafeDriveController(BaseRobot robot, double speed) {
		this.robot = robot;
		this.speed = speed;
		smoother = new VectorSmoother(SMOOTH_FACTOR);
	}

	public void controlWith(OperatorConsole cb) {
		GenericHID joystick = cb.driveJoystick;

		// Get information from joystick
		Vector driveVector = new Vector(-joystick.getX(), joystick.getY());
		double turnSpeed = -joystick.getTwist() * 2;

		driveVector = deadZone.applyTo(driveVector).times(speed);
		turnSpeed = deadZone.applyTo(turnSpeed) * speed;

		// If trigger is pressed, use fine control
		if (joystick.getTrigger())
			driveVector = driveVector.times(CRAWL_FACTOR);
		else if (joystick.getRawButton(2))
			driveVector = driveVector.times(ZOOM_FACTOR);

		driveVector = smoother.smooth(driveVector);

		robot.drive.set(driveVector, turnSpeed);
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
