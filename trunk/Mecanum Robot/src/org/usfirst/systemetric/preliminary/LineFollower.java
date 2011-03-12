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
	final long    T_WAIT_TIME     = 500;

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
		robot.grabber.tiltDown();

		// Wait for air pressure
		while (!robot.compressor.getPressureSwitchValue())
			;

		robot.grabber.grab();
		System.out.println("grab");
		Timer.delay(0.2);
		robot.grabber.tiltUp();
		System.out.println("up");

		sensor.setLinePreference(LinePreference.LEFT);
		controller.enable();
	}

	int  tCount    = 0;
	long lastTtime = -T_WAIT_TIME;

	public void autonomousContinuous() {
		long currentTime = System.currentTimeMillis();
		long timeSinceLastT = currentTime - lastTtime;
		
		
		//If we're not at a T, continue the loop
		if(!sensor.isAtT())
			return;
		
		lastTtime = currentTime;
		
		//If we're seeing the same T, continue the loop
		if (timeSinceLastT < T_WAIT_TIME)
			return;
		
		tCount++;
		
		//If this isn't the second T, continue the loop
		if (tCount != 2) 
			return;
	
		controller.disable();

		// Arm goes up
		robot.grabber.release();
		// Arm goes down
	}
	
	public void disabledInit() {
	    controller.disable();
	}
}
