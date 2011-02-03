package org.usfirst.systemetric;

import org.usfirst.systemetric.controllers.*;
import org.usfirst.systemetric.robotics.Arm;
import org.usfirst.systemetric.robotics.Grabber;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;

import org.usfirst.systemetric.sensors.ADXL345_I2C;
import org.usfirst.systemetric.sensors.Gyro;
import org.usfirst.systemetric.utils.AccelerometerAccumulator;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.HiTechnicCompass;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SmartDashboard;
import edu.wpi.first.wpilibj.SmartDashboardPacketFactory;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class RobotTest extends IterativeRobot {
	GenericHID driveJoystick;
	ControlBoard cb;
	DriverStationEnhancedIO io;
	ADXL345_I2C accelerometer;
	AccelerometerAccumulator accumulator;
	HiTechnicCompass compass = new HiTechnicCompass(4);
	

	Arm arm;

	CANJaguar jag;
	// MecanumDrive mecanumDrive;

	Controllable grabberController = new GrabberController(new Grabber(1, 2));

	// Controllable driveController = new DriveController(mecanumDrive);

	public void teleopInit() {
		accelerometer = new ADXL345_I2C(4, ADXL345_I2C.DataFormat_Range.k2G);
		accumulator = new AccelerometerAccumulator(accelerometer);
		try {
			cb = ControlBoard.getInstance();
			jag = new CANJaguar(2, ControlMode.kPercentVbus);
		} catch (CANTimeoutException e) {
			System.out.println("Could not establish connection to Jaguars");
			e.printStackTrace();
		} catch (EnhancedIOException e) {
			System.out.println("Could not establish connection to PSoC board");
			e.printStackTrace();
		}

		accelerometer.calibrate();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Gyro g;
	boolean ledState = false;

	public void teleopPeriodic() {
		grabberController.controlWith(cb);
		//ADXL345_I2C.AllAxes reading = accelerometer.getAccelerations();
		//System.out.println("Acc: " + accelerometer.getAcceleration());//reading.XAxis + "," + reading.YAxis + ","
				//+ reading.ZAxis);
		/*
		 * { ADXL345_I2C.AllAxes reading = accelerometer.getAccelerations();
		 * System.out.println("Acceleration: " + Math.sqrt(reading.XAxis *
		 * reading.XAxis + reading.YAxis reading.YAxis + reading.ZAxis *
		 * reading.ZAxis)); } { ADXL345_I2C.AllAxes reading =
		 * accelerometer.getOffsets(); System.out.println("Offset: " +
		 * Math.sqrt(reading.XAxis * reading.XAxis + reading.YAxis reading.YAxis
		 * + reading.ZAxis * reading.ZAxis)); }
		 */
		accumulator.update();
		System.out.println(accumulator.getVelocity());
		if (cb.armJoystick.getTrigger()) 
			accumulator.reset();
		System.out.println(compass.getAngle());

		// driveController.controlWith(driveJoystick);
		/*
		 * try { if (io.getDigital(1)) { ledState = !ledState; io.setLED(1,
		 * ledState); jag.setX(1); } else jag.setX(-0.5);
		 * 
		 * } catch (EnhancedIOException e) { e.printStackTrace(); } catch
		 * (CANTimeoutException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	};
}