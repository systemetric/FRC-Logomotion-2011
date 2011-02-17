package org.usfirst.systemetric.preliminary;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * @author NatY
 * Basic line following code
 */
public class LineTest extends IterativeRobot {
	DigitalInput leftSensor  = new DigitalInput(10);
	DigitalInput rightSensor = new DigitalInput(14);
	Solenoid rightSensorPower = new Solenoid(4);
	Solenoid leftSensorPower = new Solenoid(6);
	MecanumDrive robot       = OrthogonalMecanumDriveFactory.DEFAULT_ROBOT;
	Vector       forwards    = Vector.J.times(-.45);
	Vector       sideways    = Vector.ZERO;
	
	//TODO: Add PID controller to use LineTracer class

	public void autonomousContinuous() {
		boolean leftSensorOut=leftSensor.get();
		boolean rightSensorOut=rightSensor.get();
		
		if (!rightSensorOut && !leftSensorOut)
			sideways = Vector.I.times(0);//sets sideways direction to null
	
		if (!rightSensorOut && leftSensorOut)
			sideways = Vector.I.times(.35);//set sideways direction to left
		
		if (rightSensorOut && !leftSensorOut)
			sideways = Vector.I.times(-.35);//set sideways direction to right
		
		robot.setDriveVelocity(forwards.plus(sideways));
	}

	public void autonomousInit() {
		rightSensorPower.set(true);
		leftSensorPower.set(true);
	}
	
	public void teleopInit() {
		rightSensorPower.set(true);
		leftSensorPower.set(true);
	}
}
