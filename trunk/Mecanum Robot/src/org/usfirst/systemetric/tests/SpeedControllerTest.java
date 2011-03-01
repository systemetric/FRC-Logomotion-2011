package org.usfirst.systemetric.tests;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.CANJaguar.SpeedReference;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class SpeedControllerTest extends IterativeRobot {
	Joystick joy = new Joystick(1);

	public void robotInit() {
		jags[0] = makeJag(2);
		jags[1] = makeJag(3);
		jags[2] = makeJag(4);
		jags[3] = makeJag(5);
	}

	CANJaguar jags[] = new CANJaguar[4];

	private CANJaguar makeJag(int pin) {
		try {
			CANJaguar jag = new CANJaguar(pin, ControlMode.kSpeed);
			jag.setSpeedReference(SpeedReference.kQuadEncoder);
			jag.configEncoderCodesPerRev(360);
			jag.setPID(-0.4, -0.01, 0);
			jag.configMaxOutputVoltage(12);
			jag.enableControl();
			return jag;
		} catch (CANTimeoutException e) {
			System.out.println("Jag no " + pin + " did not connect");
			return null;
		}
	}

	public void teleopInit() {
	}

	public double getSpeed() {
		double pos = -joy.getY();
		if (pos > 0.05)
			return (pos - 0.05) * 600 * (1 / 0.95);
		else if (pos < -0.05)
			return (pos + 0.05) * 600 * (1 / 0.95);
		else
			return 0;
	}

	public void teleopPeriodic() {
		double speed = getSpeed();

		jags[0].set(speed);
		jags[1].set(speed);
		jags[2].set(-speed);
		jags[3].set(-speed);

		System.out.println(speed);
	}
}
