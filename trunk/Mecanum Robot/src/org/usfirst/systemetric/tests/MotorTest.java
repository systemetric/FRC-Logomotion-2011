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
			 frontRight = JaguarFactory.createSpeedController(2);
			 i++;
			 backRight = JaguarFactory.createSpeedController(3);
			 i++;
			 backLeft = JaguarFactory.createSpeedController(4);
			 i++;
			 frontLeft = JaguarFactory.createSpeedController(5);
			 i++;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Configured only "+i+" jaguars");
		}
	}

	public void autonomousPeriodic() {
		double speed = 1;
		frontRight.set(speed);
		backRight.set(speed);
		backLeft.set(-speed);
		frontLeft.set(-speed);
	}
}
