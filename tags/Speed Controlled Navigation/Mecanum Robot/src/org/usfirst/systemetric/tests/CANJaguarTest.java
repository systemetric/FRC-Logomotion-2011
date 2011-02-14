package org.usfirst.systemetric.tests;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.CANJaguar.PositionReference;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class CANJaguarTest extends IterativeRobot {
	SpeedController motor;
	Joystick joy;

	public void teleopInit() {
		try {
			CANJaguar jag = new CANJaguar(5, ControlMode.kPosition 
					/*ControlMode.kPercentVbus*/);
			jag.setPositionReference(PositionReference.kQuadEncoder);
			jag.configEncoderCodesPerRev(360);
			jag.setPID(-15, -0.2, -5);
			jag.enableControl();
			motor = jag;
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		joy = new Joystick(1);
	}

	public void teleopPeriodic() {
		if (motor != null) {
			double position = joy.getY();
			System.out.println("Position: " + position);
			motor.set(position);
		}
	}
}
