package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.util.JaguarFactory;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * Allows simple backwards and forwards movement of the robot to test the speed
 * controllers are working. Useful for tuning the PID constants in {@link JaguarFactory}
 * 
 * @author Eric
 * 
 * @see CANJaguarTest
 * 
 */
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
			return JaguarFactory.createJaguar(pin, ControlMode.kSpeed);
		} catch (CANTimeoutException e) {
			System.out.println("Jag no " + pin + " did not connect");
			return null;
		}
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
