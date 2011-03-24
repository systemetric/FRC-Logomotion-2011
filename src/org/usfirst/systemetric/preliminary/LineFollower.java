package org.usfirst.systemetric.preliminary;

import org.usfirst.systemetric.BaseRobot;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.PositionControlledArm.PegPosition;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.sensors.LineTracer;
import org.usfirst.systemetric.sensors.LineTracer.LinePreference;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class LineFollower extends IterativeRobot {
	final double  FORWARD_SPEED   = -0.5;
	final long    T_WAIT_TIME     = 500;
	
	final long    NUM_TS          = 1;
	
	BaseRobot     robot           = BaseRobot.getInstance();

	PIDController controller      = new PIDController(10, 0, 0, robot.lineSensor, new PIDOutput() {
		                              public void pidWrite(double output) {
		                            	  if(Double.isNaN(output))
		                            		  System.out.println("Uh oh...");
		                            	  robot.drive.setDriveVelocity(new Vector(output, FORWARD_SPEED));
		                              }
	                              });

	public void autonomousInit() {
        try {
	        robot.arm.moveTo(PegPosition.RESET);
        } catch (CANTimeoutException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
        }
		robot.compressor.start();
		Timer.delay(3.5);
		robot.grabber.tiltDown();

		
		Timer.delay(2);
		robot.grabber.grab();

		robot.lineSensor.setLinePreference(LinePreference.LEFT);
		controller.enable();
		
		Timer.delay(2.5);
		robot.grabber.tiltUp();
		Timer.delay(2.5);
		try {
	        robot.arm.moveTo(PegPosition.MIDDLE_OFFSET);
        } catch (CANTimeoutException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}

	int  tCount    = 0;
	long lastTtime = -T_WAIT_TIME;

	public void autonomousContinuous() {
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
		robot.grabber.release();
		// Arm goes down
	}
	
	public void disabledInit() {
	    controller.disable();
	}
}
