package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.robotics.Arm;
import org.usfirst.systemetric.robotics.DualMotorArm;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.CANJaguar.PositionReference;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class ArmTest extends IterativeRobot {
	CANJaguar jag;
	Joystick joy = new Joystick(1);
	Arm arm;
	
	//chainLinksPerMetre = 63;
	//public final double encoderRevsPerMetre = 63.0 / 13;
	
	public ArmTest() throws CANTimeoutException {
		 //jag = new CANJaguar(5);
		 //arm = new Arm(null, jag, jag, new DigitalInput(1),new DigitalInput(2));
	}
	
	public void teleopInit() {
		try {
	        arm = new Arm(6);
        } catch (CANTimeoutException e) {
	       System.err.println("Argggh!");
        }
	}
	
	public void teleopPeriodic() {
		double position = Math.max(0, joy.getY());
		arm.moveTo(position);
	}
}
