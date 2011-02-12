package org.usfirst.systemetric.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
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
	 * @param port The ID of the jaguar to initialize on the CAN bus
	 * @return Jaguar initialized for speed control, with PID values already set
	 * 
	 * @throws CANTimeoutException 
	 */
	public static CANJaguar createSpeedController(int port)
	    throws CANTimeoutException {
		try {
    		CANJaguar jag = new CANJaguar(port, CANJaguar.ControlMode.kSpeed);
    		jag.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
    		jag.enableControl();
    		jag.setPID(-SPEED_P, -SPEED_I, -SPEED_D);
    
    		jag.configNeutralMode(CANJaguar.NeutralMode.kBrake);
    		jag.configEncoderCodesPerRev(360);
    		jag.configMaxOutputVoltage(MAX_VOLTAGE);
    
    		jag.enableControl();
    
    		return jag;
		} catch (CANTimeoutException e) {
			System.err.println("Could not connect to jaguar number "+port);
			throw e;
		}
	}

	/**
	 * @param port The ID of the jaguar to initialize on the CAN bus
	 * @return Jaguar initialized for percentage control
	 * 
	 * @throws CANTimeoutException 
	 */
	public static CANJaguar createPercentageController(int port)
	    throws CANTimeoutException {
		try {
    		CANJaguar jag = new CANJaguar(port, CANJaguar.ControlMode.kPercentVbus);
    		jag.configNeutralMode(CANJaguar.NeutralMode.kBrake);
    
    		jag.configMaxOutputVoltage(MAX_VOLTAGE);
    		jag.setVoltageRampRate(VOLTAGE_RAMP_RATE);
    
    		return jag;
		} catch (CANTimeoutException e) {
			System.err.println("Could not connect to jaguar number "+port);
			throw e;
		}
	}
}
