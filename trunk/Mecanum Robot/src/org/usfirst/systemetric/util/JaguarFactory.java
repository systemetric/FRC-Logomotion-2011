package org.usfirst.systemetric.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * A class containing utility functions for creating and configuring the
 * CANJaguar objects.
 * 
 * @author Eric
 * 
 */
public class JaguarFactory {
	public static final double SPEED_P = 0.4;
	public static final double SPEED_I = 0.01;
	public static final double SPEED_D = 0;

	public static final double MAX_VOLTAGE = 12;
	public static final double VOLTAGE_RAMP_RATE = 24;

	/**
	 * @param port
	 *            The ID of the jaguar to initialize on the CAN bus
	 * @return Jaguar initialized for speed control, with PID values already set
	 * 
	 * @throws CANTimeoutException
	 * @deprecated Use createJaguar(port, ControlMode.kSpeed) instead
	 */
	public static CANJaguar createSpeedController(int port)
			throws CANTimeoutException {
		return createJaguar(port, ControlMode.kSpeed);
	}

	/**
	 * @param port
	 *            The ID of the jaguar to initialize on the CAN bus
	 * @return Jaguar initialized for percentage control
	 * 
	 * @throws CANTimeoutException
	 * @deprecated Use createJaguar(port, ControlMode.kPercentVbus) instead
	 */
	public static CANJaguar createPercentageController(int port)
			throws CANTimeoutException {
		return createJaguar(port, ControlMode.kPercentVbus);
	}

	public static CANJaguar createJaguar(int port, ControlMode mode)
			throws CANTimeoutException {
		try {
			CANJaguar jag = new CANJaguar(port, mode);
			jag.configNeutralMode(CANJaguar.NeutralMode.kBrake);
			jag.configMaxOutputVoltage(MAX_VOLTAGE);

			if (mode == ControlMode.kPercentVbus) {
				jag.setVoltageRampRate(VOLTAGE_RAMP_RATE);
			} else if (mode == ControlMode.kSpeed) {
				jag.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
				jag.configEncoderCodesPerRev(360);
				jag.setPID(-SPEED_P, -SPEED_I, -SPEED_D);
				jag.enableControl();
			}
			
			return jag;
		} catch (CANTimeoutException e) {
			System.err.println("Could not connect to jaguar number " + port);
			throw e;
		}
	}
}
