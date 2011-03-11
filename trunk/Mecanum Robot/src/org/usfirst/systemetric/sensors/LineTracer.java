package org.usfirst.systemetric.sensors;

import com.sun.squawk.util.Arrays;
import com.sun.squawk.util.Comparer;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.parsing.ISensor;

/**
 * A class representing a linear array of light detectors used to detect a line
 * on the floor. Can be used on irregular and regular linear arrays.
 * 
 * @author Eric
 * 
 */
public class LineTracer implements PIDSource, ISensor {
	public static class Detector {
		double     position;
		LineSensor sensor;

		public Detector(double position, LineSensor sensor) {
			this.position = position;
			this.sensor = sensor;
		}
	}

	public static class LinePreference {
		public final int                   value;

		public static final LinePreference CENTER = new LinePreference(0);
		public static final LinePreference LEFT   = new LinePreference(1);
		public static final LinePreference RIGHT  = new LinePreference(2);
		public static final LinePreference NONE   = new LinePreference(3);

		private LinePreference(int value) {
			this.value = value;
		}
	}

	Detector[]     detectors;
	boolean        enabled = true;

	LinePreference linePreference;

	public LineTracer(Detector[] detectors) {
		this.detectors = detectors;
		// Sort the detectors from right to left, to simplify future algorithms.
		Arrays.sort(detectors, new Comparer() {
			public int compare(Object a, Object b) {
				Detector da = (Detector) a;
				Detector db = (Detector) b;
				return (int) Math.ceil(da.position - db.position);
			}
		});
	}

	public void setLinePreference(LinePreference preference) {
		linePreference = preference;
	}

	/**
	 * Create a LineTracer object from a set of evenly spaced sensors, which
	 * detect a line between 1 and -1
	 * 
	 * @param sensors
	 *            The line sensors
	 */
	public LineTracer(LineSensor[] sensors) {
		this(sensors, 2);
	}

	/**
	 * Create a LineTracer object from a set of evenly spaced sensors spanning a
	 * specified width
	 * 
	 * @param sensors
	 *            The line sensors
	 * @param width
	 *            The distance between the two outermost sensors
	 */
	public LineTracer(LineSensor[] sensors, double width) {
		int numSensors = sensors.length;
		detectors = new Detector[numSensors];

		for (int i = 0; i < sensors.length; i++) {
			detectors[i] = new Detector(width * i / (numSensors - 1) - width / 2, sensors[i]);
		}
	}

	/**
	 * Get the position of the line, in the same units as the sensor positions
	 * were specified
	 */
	public double getLine() {
		double total = 0;
		int count = 0;
		if (linePreference == LinePreference.LEFT) {
			for (int i = 0; i < detectors.length; i++) {
				Detector d = detectors[i];
				if (d.sensor.get()) {
					count++;
					total += d.position;
				} else if (count > 0) {
					break;
				}
			}
		} else if (linePreference == LinePreference.RIGHT) {
			for (int i = detectors.length - 1; i > 0; i--) {
				Detector d = detectors[i];
				if (d.sensor.get()) {
					count++;
					total += d.position;
				} else if (count > 0) {
					break;
				}
			}
		} else {
			for (int i = 0; i < detectors.length; i++) {
				Detector d = detectors[i];
				if (d.sensor.get()) {
					count++;
					total += d.position;
				}
			}
		}
		return total / count;
	}

	public int getLineCount() {
		int count = 0;
		boolean lastValue = false;
		for (int i = 0; i < detectors.length; i++) {
			boolean value = detectors[i].sensor.get();
			if (value && !lastValue) {
				count++;
			}
			lastValue = value;
		}
		return count;
	}

	public boolean isAtT() {
		for (int i = 0; i < detectors.length; i++)
			if (!detectors[i].sensor.get())
				return false;

		return true;
	}

	public double pidGet() {
		return getLine();
	}

	public void enable() {
		for (int i = 0; i < detectors.length; i++) {
			LineSensor s = detectors[i].sensor;

			if (s instanceof LineSensor12V)
				((LineSensor12V) s).enable();
		}
		enabled = true;
	}

	public void disable() {
		for (int i = 0; i < detectors.length; i++) {
			LineSensor s = detectors[i].sensor;

			if (s instanceof LineSensor12V)
				((LineSensor12V) s).disable();
		}
		enabled = false;
	}

	public String getState() {
		StringBuffer state = new StringBuffer("(");
		for (int i = 0; i < detectors.length - 1; i++) {
			state.append(detectors[i].sensor.get()).append(", ");
		}
		state.append(detectors[detectors.length - 1].sensor.get()).append(")");
		return state.toString();
	}
}
