package org.usfirst.systemetric.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class JaguarFactory {
	public static SpeedController createSpeedController(int port)
			throws CANTimeoutException {
		CANJaguar jag = new CANJaguar(port, CANJaguar.ControlMode.kSpeed);
		
		jag.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
		jag.setPID(-10, -0.2, -2);
		jag.configNeutralMode(CANJaguar.NeutralMode.kBrake);
		jag.configEncoderCodesPerRev(360);

		jag.enableControl();

		return jag;
	}
}
