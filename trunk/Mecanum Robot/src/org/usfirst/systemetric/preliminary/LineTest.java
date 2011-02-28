package org.usfirst.systemetric.preliminary;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * @author NatY line following code
 */
public class LineTest extends IterativeRobot {
	// TODO: Find which pin the far left, right sensor is connected to.
	DigitalInput  farLeftSensor       = new DigitalInput(100000);
	DigitalInput  leftSensor          = new DigitalInput(10);
	DigitalInput  middleSensor        = new DigitalInput(12);
	DigitalInput  rightSensor         = new DigitalInput(14);
	DigitalInput  farRightSensor      = new DigitalInput(10000);

	DigitalInput  dirSwitch           = new DigitalInput(10000);                    // TODO
	                                                                                 // attach
	                                                                                 // config
	                                                                                 // switch
	                                                                                 // to
	                                                                                 // i/o
	                                                                                 // board
	Solenoid      farRightSensorPower = new Solenoid(3);
	Solenoid      rightSensorPower    = new Solenoid(5);
	Solenoid      middleSensorPower   = new Solenoid(4);
	Solenoid      leftSensorPower     = new Solenoid(7);
	Solenoid      farLeftSensorPower  = new Solenoid(8);
	MecanumDrive  robot               = OrthogonalMecanumDriveFactory.DEFAULT_ROBOT;
	Vector        forwards            = Vector.J.times(FORWARDS_FACTOR);
	Vector        sideways            = Vector.ZERO;
	static double SIDEWAYS_FACTOR     = 0.35;
	static double FORWARDS_FACTOR     = -0.45;
	boolean[][]   history             = new boolean[5][3];
	boolean       yHit                = false;

	public void autonomousContinuous() {
		boolean farLeftSensorOut = farLeftSensor.get();
		boolean leftSensorOut = leftSensor.get();
		boolean rightSensorOut = rightSensor.get();
		boolean middleSensorOut = middleSensor.get();
		boolean farRightSensorOut = farRightSensor.get();

		boolean dirSwitchOut = dirSwitch.get();

		// Update the sensor history.
		AddHistory(farLeftSensorOut, leftSensorOut, middleSensorOut, rightSensorOut,
		    farRightSensorOut);

		if (middleSensorOut) {
			if (!rightSensorOut && !leftSensorOut) {
				sideways = Vector.I.times(0);
			} // sets sideways direction to null

			if (!rightSensorOut && leftSensorOut) {
				sideways = Vector.I.times(SIDEWAYS_FACTOR); // set sideways
			} // direction to left

			if (rightSensorOut && !leftSensorOut) {
				sideways = Vector.I.times(-SIDEWAYS_FACTOR); // set sideways
			} // direction to
			  // right
			if (rightSensorOut && leftSensorOut) { // detects Y and takes a fork
				                                   // based on a config switch
				yHit = true;
				if (dirSwitchOut) {
					sideways = Vector.I.times(SIDEWAYS_FACTOR);
					forwards = Vector.J.times(0);
				} else {
					sideways = Vector.I.times(-SIDEWAYS_FACTOR);
					forwards = Vector.J.times(0);
				}
			}
		}

		else {
			if (!rightSensorOut && !leftSensorOut) {
				sideways = null; // sets sideways direction
				forwards = null; // to null, as there is no line
			}

			if (!rightSensorOut && leftSensorOut) {
				sideways = Vector.I.times(SIDEWAYS_FACTOR); // set sideways
			} // direction to left

			if (rightSensorOut && !leftSensorOut) {
				sideways = Vector.I.times(-SIDEWAYS_FACTOR); // set sideways
			} // direction to
			  // right
			if (rightSensorOut && leftSensorOut) {
				sideways = null; // detects T on field and stops
				forwards = null;
			}

		}
		if (yHit) {
			leftSensorOut = farLeftSensor.get();
			rightSensorOut = farRightSensor.get();
		}
		robot.setDriveVelocity(forwards.plus(sideways));
	}

	public void autonomousInit() {
		rightSensorPower.set(true);
		leftSensorPower.set(true);
		middleSensorPower.set(true);
		farLeftSensorPower.set(true);
		farRightSensorPower.set(true);

	}

	public void teleopInit() {
		rightSensorPower.set(true);
		leftSensorPower.set(true);
		middleSensorPower.set(true);
		farLeftSensorPower.set(true);
		farRightSensorPower.set(true);
	}

	private void AddHistory(boolean farLeft, boolean left, boolean middle, boolean right,
	    boolean farRight) {
		boolean[][] newHistory = new boolean[history.length][history[0].length];

		// Add the new sensor states to the history.
		newHistory[0][0] = farLeft;
		newHistory[0][1] = left;
		newHistory[0][2] = middle;
		newHistory[0][3] = right;
		newHistory[0][4] = farRight;

		// Copy the old history to the newHistory variable.
		for (int i = 1; i < history.length; i++) {
			for (int j = 0; j < history[i].length; j++) {
				newHistory[i][j] = history[i - 1][j];
			}
		}

		// Update the sensor history.
		history = newHistory;
	}
}
