package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.util.JaguarFactory;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.NeutralMode;
import edu.wpi.first.wpilibj.IterativeRobot;

public class MotorTest extends IterativeRobot {
	CANJaguar frontRight;
	CANJaguar backRight;
	CANJaguar backLeft;
	CANJaguar frontLeft;

	public void robotInit() {
		int i = 0;
		try {
			frontRight = JaguarFactory.createPercentageController(2);
			i++;
			backRight = JaguarFactory.createPercentageController(3);
			i++;
			backLeft = JaguarFactory.createPercentageController(4);
			i++;
			frontLeft = JaguarFactory.createPercentageController(5);
			i++;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Configured only " + i + " jaguars");
		}
	}

	public void autonomousPeriodic() {
		int i = 0;

		try {
			if(frontRight == null) frontRight = JaguarFactory.createPercentageController(2);
			frontRight.set(0.2);
			i++;
		} catch (Exception e) {
		}
		try {
			if(backRight == null)  backRight = JaguarFactory.createPercentageController(3);
			backRight.set(0.2);
			i++;
		} catch (Exception e) {
		}
		try {

			if(backLeft == null)  backLeft = JaguarFactory.createPercentageController(4);
			backLeft.set(0.2);
			i++;
		} catch (Exception e) {
		}
		try {
			if(frontLeft == null)  frontLeft = JaguarFactory.createPercentageController(5);
			frontLeft.set(0.2);
			i++;
		} catch (Exception e) {
		}

		System.out.println("Configured only " + i + " jaguars");

	}
}
