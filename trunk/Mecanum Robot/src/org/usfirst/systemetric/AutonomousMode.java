package org.usfirst.systemetric;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.PositionControlledArm.PegPosition;
import org.usfirst.systemetric.sensors.LineTracer.LinePreference;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class AutonomousMode {
	final double  FORWARD_SPEED = -0.7;
	final long    T_WAIT_TIME   = 500;

	final long    NUM_TS        = 1;

	BaseRobot     robot;
	PIDController controller;

	public AutonomousMode(BaseRobot robot) {
		this.robot = robot;

		controller = new PIDController(10, 0, 0, robot.lineSensor, new PIDOutput() {
			public void pidWrite(double output) {
				if (Double.isNaN(output))
					output = 0;
				AutonomousMode.this.robot.drive.setDriveVelocity(new Vector(output, FORWARD_SPEED));
			}
		});
	}

	public void init() {
		robot.arm.reset();
		
		//Wait for the hook to come undone
		robot.compressor.start();
		Timer.delay(3.5);
		
		//Wait for the grabber to tilt down
		robot.grabber.tiltDown();
		Timer.delay(2);
		

		//start following the line
		robot.lineSensor.setLinePreference(LinePreference.LEFT);
		controller.reset();
		controller.enable();

		//Wait for the grabber to grab
		robot.grabber.grab();
		Timer.delay(2.5);

		//Wait for the grabber to tilt up
		robot.grabber.tiltUp();
		Timer.delay(2.5);
		
		//Move the arm
		try {
			robot.arm.moveTo(PegPosition.MIDDLE_OFFSET);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
	}
	
	int  tCount    = 0;
	long lastTtime = -T_WAIT_TIME;

	public void continuous() {
		long currentTime = System.currentTimeMillis();
		long timeSinceLastT = currentTime - lastTtime;
		
		
		//If we're not at a T, continue the loop
		if(!robot.lineSensor.isAtT())
			return;
		
		System.out.println("At a T");
		
		lastTtime = currentTime;
		
		//If we're seeing the same T, continue the loop
		if (timeSinceLastT < T_WAIT_TIME)
			return;
		
		tCount++;
		
		//If this isn't the last T, continue the loop
		if (tCount != NUM_TS) 
			return;
	
		controller.disable();
		robot.drive.setDriveVelocity(Vector.ZERO);
		robot.grabber.release();
		// Arm goes down
	}
	
	public void disable() {
		controller.disable();
	}
}
