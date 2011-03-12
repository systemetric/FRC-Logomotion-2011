package org.usfirst.systemetric.preliminary;

import org.usfirst.systemetric.BaseRobot;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.sensors.LineTracer;
import org.usfirst.systemetric.sensors.LineTracer.LinePreference;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;

public class LineFollower extends IterativeRobot {
	final double  FORWARD_SPEED   = -0.25;

	boolean       hasDeployedRing = false;

	BaseRobot     robot           = BaseRobot.getInstance();
	MecanumDrive  drive           = robot.drive;
	LineTracer    sensor          = robot.lineSensor;

	PIDController controller      = new PIDController(10, 0, 0, sensor, new PIDOutput() {
		                              public void pidWrite(double output) {
			                              drive.setDriveVelocity(new Vector(output, FORWARD_SPEED));
		                              }
	                              });

	public void autonomousInit() {
		robot.compressor.start();
		System.out.println("on");
		Timer.delay(
			2);
		robot.grabber.tiltDown();
		System.out.println("down");
		Timer.delay(1);
		robot.grabber.grab();
		System.out.println("grab");
		Timer.delay(1);
		robot.grabber.tiltUp();
		System.out.println("up");

		sensor.setLinePreference(LinePreference.LEFT);
		controller.enable();
	}

	public void autonomousContinuous() {
		if (!hasDeployedRing && sensor.isAtT()) {
			controller.disable();

			hasDeployedRing = true;
			
			// Arm goes up
			robot.grabber.release();
			//Arm goes down
		}
	}
}
