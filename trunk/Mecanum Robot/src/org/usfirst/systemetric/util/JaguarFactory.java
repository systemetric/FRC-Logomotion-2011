package org.usfirst.systemetric.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class JaguarFactory {
	public static CANJaguar createSpeedController(int port)
	    throws CANTimeoutException {
		CANJaguar jag = new CANJaguar(port, CANJaguar.ControlMode.kSpeed);

		jag.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
		jag.setPID(-0.1, 0, 0);
		jag.configNeutralMode(CANJaguar.NeutralMode.kBrake);
		jag.configEncoderCodesPerRev(360);
		jag.configMaxOutputVoltage(6);

		jag.enableControl();

		return jag;
	}

	public static CANJaguar createPercentageController(int port)
	    throws CANTimeoutException {
		CANJaguar jag = new CANJaguar(port, CANJaguar.ControlMode.kPercentVbus);
		
		jag.configMaxOutputVoltage(6);
		jag.setVoltageRampRate(2);

		return jag;
	}
}
