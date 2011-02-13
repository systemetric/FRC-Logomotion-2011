package org.usfirst.systemetric.sensors;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.parsing.ISensor;

/**
 * A class represent a linear array of light detectors used to detect a line on
 * the floor. Can be used on irregular and regular linear arrays.
 * 
 * @author Eric
 * 
 */
public class LineTracer implements PIDSource, ISensor {
	public static class Detector {
		double position;
		DigitalInput sensor;

		public Detector(double position, DigitalInput sensor) {
			this.position = position;
			this.sensor = sensor;
		}
	}

	Detector[] detectors;

	public LineTracer(Detector[] detectors) {
		this.detectors = detectors;
	}

	/**
	 * Create a LineTracer object from a set of evenly spaced sensors, which
	 * detect a line between 1 and -1
	 * 
	 * @param sensors
	 *            The line sensors
	 */
	public LineTracer(DigitalInput[] sensors) {
		int numSensors = sensors.length;
		detectors = new Detector[numSensors];

		for (int i = 0; i < sensors.length; i++) {
			detectors[i] = new Detector(2 * i / (numSensors - 1) - 1,
					sensors[i]);
		}
	}

	/**
	 * Get the position of the line, in the same units as the sensor positions
	 * were specified
	 */
	public double getLine() {
		double total = 0;
		int count = 0;
		for (int i = 0; i < detectors.length; i++) {
			Detector d = detectors[i];
			if (d.sensor.get()) {
				count++;
				total += d.position;
			}
		}
		return total / count;
	}

	public double pidGet() {
		return getLine();
	}
}
