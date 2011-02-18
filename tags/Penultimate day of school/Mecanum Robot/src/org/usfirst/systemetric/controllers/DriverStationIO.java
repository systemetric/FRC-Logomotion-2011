package org.usfirst.systemetric.controllers;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.tDigitalConfig;
import edu.wpi.first.wpilibj.parsing.IInputOutput;

public class DriverStationIO {
	public static edu.wpi.first.wpilibj.DriverStationEnhancedIO io = DriverStation
			.getInstance().getEnhancedIO();

	private DriverStationIO() {
	}

	public static class DigitalIO implements IInputOutput {
		int channel;

		public DigitalIO(int channel, tDigitalConfig config)
				throws EnhancedIOException {
			this.channel = channel;
			io.setDigitalConfig(channel, config);
		}

		public boolean get() throws EnhancedIOException {
			return io.getDigital(channel);
		}
	}

	public static class DigitalInput extends DigitalIO {
		public DigitalInput(int channel) throws EnhancedIOException {
			super(channel, tDigitalConfig.kInputPullDown);
		}
	}

	public static class DigitalOutput extends DigitalIO {
		public DigitalOutput(int channel) throws EnhancedIOException {
			super(channel, tDigitalConfig.kOutput);
		}

		public void set(boolean value) throws EnhancedIOException {
			io.setDigitalOutput(channel, value);
		}
	}
}
