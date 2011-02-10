package org.usfirst.systemetric.tests;

import javax.microedition.midlet.MIDlet;

import org.usfirst.systemetric.ControlBoard;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class MecanumTest extends IterativeRobot {
	MecanumDrive robot;
	Joystick     joy = new Joystick(1);

	public void robotInit() {
		Timer.delay(2);
		Vector size = new Vector(0.55, 0.7);
		double wheelRadius = 0.075;
		double gearRatio = 19.0 / 36.0;
		try {
			robot = OrthogonalMecanumDriveFactory.createMecanumDrive(size);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	public void teleopPeriodic() {

		Vector direction = new Vector(joy.getX(), -joy.getY());
		System.out.println(direction);
		
		boolean left = joy.getRawButton(4);
		boolean right = joy.getRawButton(5);
		
		robot.setDriveVelocity(direction.times(joy.getTrigger() ? 1 : 0.75));
		
		robot.setTurnVelocity(left ? -1 : right ? 1 : 0);

	}
}
