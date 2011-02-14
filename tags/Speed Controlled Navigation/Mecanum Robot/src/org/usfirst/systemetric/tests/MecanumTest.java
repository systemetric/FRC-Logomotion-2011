package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.geometry.*;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.sensors.HiTechnicCompass;
import org.usfirst.systemetric.util.JaguarFactory;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * Basic drive code for testing whether the Mecanum code works in real life.
 * Also tests the two arm Jaguars
 * 
 * @author Eric
 * 
 */
public class MecanumTest extends IterativeRobot {
	MecanumDrive     robot;
	Joystick         joy = new Joystick(1);

	CANJaguar        arm1;
	CANJaguar        arm2;

	HiTechnicCompass compass;

	public void robotInit() {
		Timer.delay(2);
		
		Vector size = new Vector(0.55, 0.7);
		double wheelRadius = 0.075;
		double gearRatio = 19.0 / 36.0;
		
		try {
			robot = OrthogonalMecanumDriveFactory.createMecanumDrive(size,
			    wheelRadius, gearRatio);
			
			//Instantiate arm Jaguars
			arm1 = JaguarFactory.createPercentageController(6);
			arm2 = JaguarFactory.createPercentageController(7);
			
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		compass = new HiTechnicCompass(4);
	}

	public void teleopPeriodic() {

		Vector direction = new Vector(joy.getX(), joy.getY());
		Matrix rotation = Matrix.fromRotation(Math.toRadians(-compass
		    .getAngle()));
		direction = rotation.times(direction);
		// System.out.println(direction);

		boolean left = joy.getRawButton(4);
		boolean right = joy.getRawButton(5);

		if (joy.getRawButton(11)) {
			robot.setDriveVelocity(new Vector(0, 0));
			robot.setTurnVelocity(2000);
			Timer.delay(0.25);
			
			//Calibrate the compass
			compass.startCalibration();
			Timer.delay(16);
			compass.stopCalibration();
		}

		robot
		    .setDriveVelocity(direction.times(joy.getTrigger() ? 10000 : 5000));

		robot.setTurnVelocity(left ? -2000 : right ? 2000 : 0);
		arm1.set(joy.getRawButton(6) ? 1 : -1);
		arm2.set(joy.getRawButton(7) ? 1 : -1);

		System.out.println(compass.getAngle());
	}
}
