package org.usfirst.systemetric.sensors;

import com.sun.squawk.util.Arrays;
import com.sun.squawk.util.Comparer;
import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.DigitalInput;
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
		double       position;
		DigitalInput sensor;

		public Detector(double position, DigitalInput sensor) {
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

	LinePreference linePreference;

	public LineTracer(Detector[] detectors) {
		this.detectors = detectors;
		Arrays.sort(detectors, new Comparer() {
			public int compare(Object a, Object b) {
				Detector da = (Detector) a;
				Detector db = (Detector) b;
				return (int) Math.ceil(da.position - db.position);
			}
		});
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
			detectors[i] = new Detector(2 * i / (numSensors - 1) - 1, sensors[i]);
		}
	}

	/**
	 * Get the position of the line, in the same units as the sensor positions
	 * were specified
	 */
	public double getLine() {
		double total = 0;
		int count = 0;
		if(linePreference == LinePreference.LEFT) {
    		for (int i = 0; i < detectors.length; i++) {
    			Detector d = detectors[i];
    			if (d.sensor.get()) {
    				count++;
    				total += d.position;
    			} else if(count > 0) {
    				break;
    			}
    		}
		} else if(linePreference == LinePreference.RIGHT) {
    		for (int i = detectors.length - 1; i > 0 ; i--) {
    			Detector d = detectors[i];
    			if (d.sensor.get()) {
    				count++;
    				total += d.position;
    			} else if(count > 0) {
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

	public double pidGet() {
		return getLine();
	}
}
