package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.robotics.Arm;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class ArmTest extends IterativeRobot {
	CANJaguar jag;
	Joystick joy = new Joystick(1);
	Arm arm;
	
	public ArmTest() throws CANTimeoutException {
		 jag = new CANJaguar(5);
		 arm = new Arm(null, jag, jag, new DigitalInput(1),new DigitalInput(2));
	}
	
	public void teleopPeriodic() {
		arm.setSpeed(joy.getY());
	}
}
