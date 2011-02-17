package org.usfirst.systemetric.robotics;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.systemetric.robotics.CopyOfArm.PegPosition;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.CANJaguar.PositionReference;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.parsing.IMechanism;

public class CopyOfArm implements IMechanism {
	// Chain pitch = 8mm
	public final static double metresPerEncoderRev = 0.008 * 13;
	public final static double heightTolerance     = 0.1;

	volatile PegPosition       targetPosition      = null;
	java.util.Timer            controlLoop         = new Timer();
	public CANJaguar           jag;
	double                     positionOffset      = 0;

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

	public CopyOfArm(int canId) throws CANTimeoutException {
		jag = new CANJaguar(canId);
		disablePositionControl();
		controlLoop.schedule(new ArmTask(), 0, 20);

	}

	private void enablePositionControl(PegPosition target) throws CANTimeoutException {
		if (targetPosition == null) {
			jag.changeControlMode(ControlMode.kPosition);
			jag.setPositionReference(PositionReference.kQuadEncoder);
			jag.configEncoderCodesPerRev(6);
			jag.setPID(100, 0.05, 0);
			jag.configMaxOutputVoltage(12);
			jag.enableControl();
			
			targetPosition = target;
		}
	}

	private void disablePositionControl() throws CANTimeoutException {
		if(targetPosition != null) {
    		jag.disableControl();
    		jag.changeControlMode(ControlMode.kPercentVbus);
    		jag.setVoltageRampRate(24);
    		jag.configMaxOutputVoltage(12);
    		jag.enableControl();
    		targetPosition = null;
		}
	}

	public void setSpeed(double speed) throws CANTimeoutException {
		System.out.println("Trying to set speed...");
		synchronized (controlLoop) {
			System.out.println("Set speed to " + speed);
			disablePositionControl();
			targetPosition = null;
			jag.setX(speed);
		}
	}

	public void moveTo(PegPosition position) throws CANTimeoutException {
		System.out.println("Trying to set position...");
		synchronized (controlLoop) {
			enablePositionControl(position);
			targetPosition = position;
			System.out.println("Set position to " + position.height);
		}
	}

	public double getHeight() throws CANTimeoutException {
		return metresPerEncoderRev * (jag.getPosition() - positionOffset);
	}

	private class ArmTask extends TimerTask {

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
			if (!DriverStation.getInstance().isEnabled())
				return;
			
			try {
				handleLimits();
				/*synchronized (this) {
					if (targetPosition != null) {
						double position = jag.getPosition() - positionOffset;

						boolean inPosition = Math.abs(position - targetPosition.encoderCount)
						    * metresPerEncoderRev < heightTolerance;

						if (inPosition) {
							targetPosition = null;
							jag.configMaxOutputVoltage(0);
						} else if (!inPosition) {
							jag.configMaxOutputVoltage(12);
							jag.setX(targetPosition.encoderCount + positionOffset);
						}
					}
				}*/
			} catch (CANTimeoutException e) {
				e.printStackTrace();
			}
		}
	}

}
