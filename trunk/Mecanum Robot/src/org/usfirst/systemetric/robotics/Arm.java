package org.usfirst.systemetric.robotics;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.CANJaguar.PositionReference;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.parsing.IMechanism;

public class Arm implements IMechanism {
	public final static double encoderRevsPerMetre = 63.0 / 13;
	
	public static class PegPosition {
		final int BOTTOM = 1;
		final int BOTTOM_OFFSET = 2;
		final int MIDDLE = 3;
		final int MIDDLE_OFFSET = 4;
		final int TOP_OFFSET = 6;
	}
	
	CANJaguar jag;
	
	public Arm(int canId) throws CANTimeoutException {
		jag = new CANJaguar(canId, ControlMode.kPosition);
        jag.setPositionReference(PositionReference.kQuadEncoder);
		jag.configEncoderCodesPerRev(6);
		jag.setPID(100, 0.01, 0);
		jag.enableControl();
	}
	
	public void moveTo(double height) {
		height = Math.max(0, height);
		jag.set(encoderRevsPerMetre * height);
	}

}
