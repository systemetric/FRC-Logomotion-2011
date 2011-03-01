package org.usfirst.systemetric.robotics;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.parsing.IMechanism;

public class Arm implements IMechanism {
	// Chain pitch = 8mm
	public final static double metresPerEncoderRev = 0.008 * 13;
	public final static double heightTolerance     = 0.1;

	volatile double     targetPosition      = 0;
	java.util.Timer     controlLoop         = new Timer();
	public CANJaguar           jag;
	ControlMode         controlMode;
	
	double              positionOffset = 0;

	/**
	 * Set of positions that the arm can be placed in
	 * 
	 */
	public static class PegPosition {
		public final double             height;
		public final double             encoderCount;

		public static final PegPosition BOTTOM_LIMIT  = new PegPosition(0.75);
		
		public static final PegPosition BOTTOM        = new PegPosition(0.75);
		public static final PegPosition BOTTOM_OFFSET = new PegPosition(0.95);
		public static final PegPosition MIDDLE        = new PegPosition(1.65);
		public static final PegPosition MIDDLE_OFFSET = new PegPosition(1.85);
		
		public static final PegPosition TOP_LIMIT     = new PegPosition(2.00);

		private PegPosition(double height) {
			this.height = height;
			this.encoderCount = height / metresPerEncoderRev;
		}
	}

	public Arm(int canId) {
		try {
			jag = new CANJaguar(canId, ControlMode.kPercentVbus);
	        jag.setVoltageRampRate(24);
			jag.configMaxOutputVoltage(12);

        } catch (CANTimeoutException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	public void setSpeed(double speed) throws CANTimeoutException {
		System.out.println("Set speed to " + speed);
		jag.setX(speed);
	}
	private class ArmTask extends TimerTask {
		boolean enabled        = true;

		/**
		 * Handle the limits on the arm, and update the position count to
		 * reflect this
		 * 
		 * @throws CANTimeoutException
		 */
		private void handleLimits() throws CANTimeoutException {
			double jagPosition = jag.getPosition();

			// Arm is at top
			if (!jag.getForwardLimitOK()) {
				double actualPosition = PegPosition.TOP_LIMIT.height;				
				positionOffset = jagPosition - actualPosition;
			}

			// Arm is at bottom
			if (!jag.getReverseLimitOK()) {
				double actualPosition = PegPosition.BOTTOM_LIMIT.height;
				positionOffset = jagPosition - actualPosition;
			}
		}

		public void run() {
			if(!DriverStation.getInstance().isEnabled()) return;
			try {
				handleLimits();

				if (controlMode == ControlMode.kPosition) {
					double position = jag.getPosition() - positionOffset;

					boolean inPosition = Math.abs(position - targetPosition)
					    * metresPerEncoderRev < heightTolerance;

					if (enabled && inPosition) {
						enabled = false;
						jag.configMaxOutputVoltage(0);
					} else if (!enabled && !inPosition) {
						enabled = true;
						jag.configMaxOutputVoltage(12);
						jag.setX(targetPosition + positionOffset);
						System.out.println();
					} else if (enabled && !inPosition) {
						jag.setX(targetPosition + positionOffset);
					}
				}
			} catch (CANTimeoutException e) {
				e.printStackTrace();
			}
		}
	}

}
