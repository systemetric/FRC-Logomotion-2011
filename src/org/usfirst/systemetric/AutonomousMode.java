package org.usfirst.systemetric;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.PositionControlledArm.PegPosition;
import org.usfirst.systemetric.sensors.LineTracer.LinePreference;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class AutonomousMode {
	final double  FORWARD_SPEED = -0.8;
	final long    T_WAIT_TIME   = 500;

	final long    NUM_TS        = 1;

	BaseRobot     robot;
	PIDController controller;
	
	boolean initFinished;

	public AutonomousMode(BaseRobot robot) {
		this.robot = robot;
		initFinished = false;
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
		
		//Wait for the grabber to tilt down
		Timer.delay(2.5);
		robot.grabber.tiltDown();
		

		//Wait for the grabber to grab
		Timer.delay(7);
		robot.grabber.grab();
		//Timer.delay(1);
		
		//start following the line
		robot.lineSensor.setLinePreference(LinePreference.LEFT);
		controller.reset();
		controller.enable();

		//Wait for the grabber to tilt up
		robot.grabber.tiltUp();
		
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
		
		//Wait until the arm is in position
		//while(!robot.arm.inPosition());
		
		robot.grabber.release();
		Timer.delay(0.5);
		robot.drive.setDriveVelocity(new Vector(0, 0.2));
	}
	
	public void disable() {
		controller.disable();
		robot.grabber.release();
	}
}
