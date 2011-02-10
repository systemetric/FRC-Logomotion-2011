package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class MecanumTest extends IterativeRobot {
	MecanumDrive robot;
	Joystick joy = new Joystick(1);

	public void robotInit() {
		Vector size = new Vector(0.55, 0.7);
		double wheelRadius = 0.075;
		double gearRatio = 19.0 / 36.0;

		try {
			robot = OrthogonalMecanumDriveFactory.createMecanumDrive(size,
					wheelRadius, gearRatio);
		} catch (CANTimeoutException e) {
			System.out.println("Argghh, the Jaguars are not connected properly!");
			System.exit(-1);
		}
	}

	public void teleopPeriodic() {
		Vector direction = new Vector(joy.getX(), joy.getY());
		System.out.println(direction);
		robot.setDriveVelocity(direction.times(0.1));
	}
}
