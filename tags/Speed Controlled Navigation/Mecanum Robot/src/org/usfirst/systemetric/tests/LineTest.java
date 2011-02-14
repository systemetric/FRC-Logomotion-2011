package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * @author NatY
 * Basic line following code
 */
public class LineTest extends IterativeRobot {
	DigitalInput leftSensor  = new DigitalInput(1);
	DigitalInput rightSensor = new DigitalInput(2);
	MecanumDrive robot       = OrthogonalMecanumDriveFactory.DEFAULT_ROBOT;
	Vector       forwards    = Vector.J.times(5);
	Vector       sideways    = Vector.ZERO;

	public void autonomousContinuous() {
		boolean leftSensorOut=leftSensor.get();
		boolean rightSensorOut=rightSensor.get();
		
		if (!rightSensorOut||!leftSensorOut)
			sideways = Vector.I.times(0);//sets sideways direction to null
	
		if (!rightSensorOut||leftSensorOut)
			sideways = Vector.I.times(-5);//set sideways direction to left
		
		if (rightSensorOut||!leftSensorOut)
			sideways = Vector.I.times(5);//set sideways direction to right
		
		robot.setDriveVelocity(forwards.plus(sideways));
	}

	public void autonomousInit() {
		super.autonomousInit();
	}
}
