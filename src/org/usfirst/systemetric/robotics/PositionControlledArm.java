package org.usfirst.systemetric.robotics;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.CANJaguar.PositionReference;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SmartDashboard;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.parsing.IMechanism;

public class PositionControlledArm implements IMechanism {
	// Chain pitch = 8mm
	public final static double metresPerEncoderRev = 0.008 * 13;
	public final static double heightTolerance     = 0.025;

	volatile PegPosition       targetPosition      = PegPosition.RESET;
	java.util.Timer            controlLoop         = new Timer();
	boolean                    doFeedback          = false;
	public CANJaguar           jag;
	volatile double            positionOffset      = 0;

	/**
	 * Set of positions that the arm can be placed in
	 * 
	 */
	public static class PegPosition {
		public double             height;
		public double             encoderCount;

		public static final PegPosition RESET         = new PegPosition(-10.0);
		public static final PegPosition BOTTOM_LIMIT  = new PegPosition(0.75);

		public static final PegPosition BOTTOM        = new PegPosition(0.76);
		public static final PegPosition BOTTOM_OFFSET = new PegPosition(0.97);
		public static final PegPosition MIDDLE        = new PegPosition(1.69);
		public static final PegPosition MIDDLE_OFFSET = new PegPosition(1.91);

		public static final PegPosition TOP_LIMIT     = new PegPosition(2.00);

		private static PegPosition custom = new PegPosition(Double.NaN);
		
		private PegPosition(double height) {
			this.height = height;
			this.encoderCount = height / metresPerEncoderRev;
		}

		public String toString() {
			return Double.toString(this.height);
		}

		public static PegPosition custom(double height) {
			custom.height = height;
			custom.encoderCount = height / metresPerEncoderRev;
			return custom;
		}
	}

	public PositionControlledArm(int canId) {
		try {
			jag = new CANJaguar(canId);
			configPositionControl();
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}

		controlLoop.schedule(new ArmTask(), 0, 20);

	}

	private void configPositionControl() {
		try {
			jag.changeControlMode(ControlMode.kPosition);
			jag.setPositionReference(PositionReference.kQuadEncoder);
			jag.configEncoderCodesPerRev(6);
			jag.setPID(250, 0.05, 0);
			jag.configMaxOutputVoltage(12);

			jag.enableControl();
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
	}

	public void moveTo(PegPosition position) throws CANTimeoutException {
		if (position == null)
			return;

		targetPosition = position;
		doFeedback = true;
	}
	
	public double getVoltage() {
		try {
	        return jag.getOutputVoltage();
        } catch (CANTimeoutException e) {
	        return Double.NaN;
        }
	}

	public boolean inPosition() {
		try {
			return Math.abs(getHeight() - targetPosition.height) < heightTolerance;
		} catch (CANTimeoutException e) {
			return false;
		}
	}

	public void reset() {
		try {
			moveTo(PegPosition.RESET);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
	}

	private double position;
	
	public double getHeight() throws CANTimeoutException {
		return metresPerEncoderRev * (position - positionOffset);
	}

	public PegPosition getTarget() {
		return targetPosition;
	}
	
	volatile String state;
	
	public String getState() {
		return state;
	}

	private class ArmTask extends TimerTask {

		/**
		 * Handle the limits on the arm, and update the position count to
		 * reflect this
		 * 
		 * @throws CANTimeoutException
		 */
		private void handleLimits() throws CANTimeoutException {

			// Arm is at top
			if (!jag.getForwardLimitOK()) {
				double actualPosition = PegPosition.TOP_LIMIT.encoderCount;
				positionOffset = position - actualPosition;

				state = "Top limit";
			}

			// Arm is at bottom
			if (!jag.getReverseLimitOK()) {
				double actualPosition = PegPosition.BOTTOM_LIMIT.encoderCount;
				positionOffset = position - actualPosition;

				if (targetPosition == PegPosition.RESET)
					targetPosition = PegPosition.BOTTOM;

				state = "Bottom limit";
			}
		}

		public void run() {
			if (!DriverStation.getInstance().isEnabled())
				return;

			try {
				position = jag.getPosition();
				handleLimits();

				if (targetPosition != null && doFeedback) {
					if (inPosition()) {
						state = "In position";
						doFeedback = false;
					} else {
						jag.configMaxOutputVoltage(12);
						jag.setX(targetPosition.encoderCount + positionOffset);
					}
				} else {
					jag.configMaxOutputVoltage(0);
				}
			} catch (CANTimeoutException e) {
				e.printStackTrace();
			}
		}
	}
}
