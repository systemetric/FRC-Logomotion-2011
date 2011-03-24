package org.usfirst.systemetric.junk;

import org.usfirst.systemetric.OperatorConsole;
import org.usfirst.systemetric.controllers.Controller;
import org.usfirst.systemetric.controllers.GrabberController;
import org.usfirst.systemetric.robotics.DualMotorArm;
import org.usfirst.systemetric.robotics.Grabber;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.sensors.ADXL345_I2C;
import org.usfirst.systemetric.sensors.Gyro;
import org.usfirst.systemetric.util.AccelerometerAccumulator;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.HiTechnicCompass;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class CopyOfRobotTest extends IterativeRobot {
	GenericHID driveJoystick;
	OperatorConsole cb;
	DriverStationEnhancedIO io;
	ADXL345_I2C accelerometer;
	AccelerometerAccumulator accumulator;
	HiTechnicCompass compass;
	DualMotorArm arm;

	CANJaguar jag;
	MecanumDrive mecanumDrive;

	Controller grabberController = new GrabberController(new Grabber(1, 2));
	//Controllable driveController = new DriveController(mecanumDrive);

	public void robotInit() {
		try {
			accelerometer = new ADXL345_I2C(4, ADXL345_I2C.DataFormat_Range.k2G);
			accumulator = new AccelerometerAccumulator(accelerometer);

			accelerometer.calibrate();
			
			compass = new HiTechnicCompass(4);
		} catch (Exception e) {
			System.out.println("ARGGG");
		}

		try {
			jag = new CANJaguar(2, ControlMode.kPercentVbus);
		} catch (CANTimeoutException e) {
			System.out.println("Could not establish connection to Jaguars");
			e.printStackTrace();
		}
	}

	public void teleopInit() {
		DriverStationLCD.getInstance().println(Line.kUser2, 1, "Hello");
		DriverStationLCD.getInstance().updateLCD();

		try {
			cb = OperatorConsole.getInstance();
		} catch (EnhancedIOException e) {
			System.out.println("Could not establish connection to PSoC board");
			e.printStackTrace();
		}
	}

	Gyro g;
	boolean ledState = false;

	public void teleopPeriodic() {
		DriverStationLCD lcd = DriverStationLCD.getInstance();

		grabberController.controlWith(cb);
		//driveController.controlWith(cb);
		
		// ADXL345_I2C.AllAxes reading = accelerometer.getAccelerations();
		// reading.XAxis + "," + reading.YAxis + "," + reading.ZAxis);
		
		System.out.println("Acc: " + accelerometer.getAcceleration());
		
		accumulator.update();
		System.out.println("Vel: " + accumulator.getVelocity());

		/*
		 * { ADXL345_I2C.AllAxes reading = accelerometer.getAccelerations();
		 * System.out.println("Acceleration: " + Math.sqrt(reading.XAxis *
		 * reading.XAxis + reading.YAxis reading.YAxis + reading.ZAxis *
		 * reading.ZAxis)); } { ADXL345_I2C.AllAxes reading =
		 * accelerometer.getOffsets(); System.out.println("Offset: " +
		 * Math.sqrt(reading.XAxis * reading.XAxis + reading.YAxis reading.YAxis
		 * + reading.ZAxis * reading.ZAxis)); }
		 */
		
		if (cb.armJoystick.getTrigger())
			accumulator.reset();
		
		//System.out.println(compass.getAngle());
		lcd.println(Line.kMain6, 1, "" + compass.getAngle());
		lcd.updateLCD();


		try {
			if (io.getDigital(1)) {
				ledState = !ledState;
				io.setLED(1, ledState);
				jag.setX(1);
			} else
				jag.setX(-0.5);

		} catch (EnhancedIOException e) {
			e.printStackTrace();
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}

	};
}