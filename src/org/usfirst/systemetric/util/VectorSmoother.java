package org.usfirst.systemetric.util;

import org.usfirst.systemetric.geometry.Vector;

/**
 * Smoothes vectors for acceleration
 * 
 * @author NatY
 * 
 */
public class VectorSmoother {
	double smoothRate;
	Vector output;

	public VectorSmoother(double smoothRate) {
		if (smoothRate > 1 || smoothRate < 0)
			throw new IllegalArgumentException(
					"smooth rate must be between 1 and 0");

		this.smoothRate = smoothRate;
		reset();
	}

	/**
	 * gives the smoother a new value to smooth
	 * 
	 * @param input
	 *            the vector to be smoothed
	 */
	public void setInput(Vector input) {
		if (output == Vector.ZERO)
			output = input;
		else
			output = input.times(smoothRate).plus(output.times(1 - smoothRate));

	}

	public Vector getOutput() {
		return output;
	}

	public void reset() {
		output = Vector.ZERO;
	}

	/**
	 * Smoothes the input vector
	 * 
	 * @param input
	 *            the vector to be smoothed
	 * @return the smoothed vector
	 */
	public Vector smooth(Vector input) {
		setInput(input);
		return getOutput();
	}
}
