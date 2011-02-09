/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.systemetric.sensors;

import org.usfirst.systemetric.geometry.Vector;

import edu.wpi.first.wpilibj.*;

/**
 * 
 * @author dtjones
 * 
 *         Modified to allow access to offset registers
 */
public class ADXL345_I2C extends SensorBase {

	private static final byte kAddress = 0x3A;
	private static final byte kPowerCtlRegister = 0x2D;
	private static final byte kDataFormatRegister = 0x31;
	private static final byte kDataRegister = 0x32;
	private static final byte kOffsetRegister = 0x1E;

	public final static double kGsPerOffsetLSB = 0.0156;
	private static final byte kPowerCtl_Link = 0x20,
			kPowerCtl_AutoSleep = 0x10, kPowerCtl_Measure = 0x08,
			kPowerCtl_Sleep = 0x04;
	private static final byte kDataFormat_SelfTest = (byte) 0x80,
			kDataFormat_SPI = 0x40, kDataFormat_IntInvert = 0x20,
			kDataFormat_FullRes = 0x08, kDataFormat_Justify = 0x04;

	public static class DataFormat_Range {

		/**
		 * The integer value representing this enumeration
		 */
		public final byte value;
		public final double LSBperG;

		static final byte k2G_val = 0x00;
		static final byte k4G_val = 0x01;
		static final byte k8G_val = 0x02;
		static final byte k16G_val = 0x03;

		public static final DataFormat_Range k2G = new DataFormat_Range(
				k2G_val, 256);
		public static final DataFormat_Range k4G = new DataFormat_Range(
				k4G_val, 128);
		public static final DataFormat_Range k8G = new DataFormat_Range(
				k8G_val, 64);
		public static final DataFormat_Range k16G = new DataFormat_Range(
				k16G_val, 32);

		private DataFormat_Range(byte value, double LSBperG) {
			this.value = value;
			this.LSBperG = LSBperG;
		}
	}

	DataFormat_Range dataFormat_Range;

	public static class Axes {

		/**
		 * The integer value representing this enumeration
		 */
		public final byte value;
		static final byte kX_val = 0x00;
		static final byte kY_val = 0x02;
		static final byte kZ_val = 0x04;
		public static final Axes kX = new Axes(kX_val);
		public static final Axes kY = new Axes(kY_val);
		public static final Axes kZ = new Axes(kZ_val);

		private Axes(byte value) {
			this.value = value;
		}
	}

	public static class AllAxes {

		public double XAxis;
		public double YAxis;
		public double ZAxis;
	}

	private I2C m_i2c;

	/**
	 * Constructor.
	 * 
	 * @param slot
	 *            The slot of the digital module that the sensor is plugged
	 *            into.
	 * @param range
	 *            The range (+ or -) that the accelerometer will measure.
	 */
	public ADXL345_I2C(int slot, DataFormat_Range range) {
		dataFormat_Range = range;

		DigitalModule module = DigitalModule.getInstance(slot);
		m_i2c = module.getI2C(kAddress);

		// Turn on the measurements
		m_i2c.write(kPowerCtlRegister, kPowerCtl_Measure);
		// Specify the data format to read
		m_i2c.write(kDataFormatRegister, kDataFormat_FullRes | range.value);
	}

	/**
	 * Get the acceleration of one axis in Gs.
	 * 
	 * @param axis
	 *            The axis to read from.
	 * @return Acceleration of the ADXL345 in Gs.
	 */
	public double getAcceleration(Axes axis) {
		byte[] rawAccel = new byte[2];

		m_i2c.read(kDataRegister + axis.value, rawAccel.length, rawAccel);

		// Sensor is little endian... swap bytes
		return accelFromBytes(rawAccel[0], rawAccel[1]);
	}
	
	private int rawAccelFromBytes(byte first, byte second) {
		short tempLow = (short) (first & 0xff);
		short tempHigh = (short) ((second << 8) & 0xff00);
		return (tempLow | tempHigh);
	}

	private double accelFromBytes(byte first, byte second) {
		return rawAccelFromBytes(first, second) / dataFormat_Range.LSBperG;
	}

	/**
	 * Get the acceleration of all axes in Gs.
	 * 
	 * @return Acceleration measured on all axes of the ADXL345 in Gs.
	 */
	public AllAxes getAccelerations() {
		AllAxes data = new AllAxes();
		byte[] rawData = new byte[6];
		if (!m_i2c.read(kDataRegister, rawData.length, rawData)) {

			// Sensor is little endian... swap bytes
			data.XAxis = accelFromBytes(rawData[0], rawData[1]);
			data.YAxis = accelFromBytes(rawData[2], rawData[3]);
			data.ZAxis = accelFromBytes(rawData[4], rawData[5]);
			return data;

		} else {
			return null;
		}
		/*
		 * data.XAxis = Double.NaN; data.YAxis = Double.NaN; data.ZAxis =
		 * Double.NaN;
		 */
	}
	
	public AllAxes getRawAccelerations() {
		AllAxes data = new AllAxes();
		byte[] rawData = new byte[6];
		if (!m_i2c.read(kDataRegister, rawData.length, rawData)) {

			// Sensor is little endian... swap bytes
			data.XAxis = rawAccelFromBytes(rawData[0], rawData[1]);
			data.YAxis = rawAccelFromBytes(rawData[2], rawData[3]);
			data.ZAxis = rawAccelFromBytes(rawData[4], rawData[5]);
			return data;

		} else {
			return null;
		}
		/*
		 * data.XAxis = Double.NaN; data.YAxis = Double.NaN; data.ZAxis =
		 * Double.NaN;
		 */
	}

	public Vector getHorizontalAcceleration() {
		AllAxes acc = getAccelerations();
		return acc == null ? new Vector(Double.NaN, Double.NaN) : new Vector(
				acc.XAxis, acc.YAxis).times(9.81);
	}

	public double getOffset(Axes axis) {
		byte[] data = new byte[1];
		m_i2c.read(kOffsetRegister + axis.value, 1, data);

		return data[0] * kGsPerOffsetLSB;
	}

	public void setOffset(Axes axis, double offset) {
		byte rawOffset = (byte) (offset / kGsPerOffsetLSB);
		m_i2c.write(kOffsetRegister + axis.value, rawOffset);
	}

	public AllAxes getOffsets() {
		AllAxes axes = new AllAxes();
		byte[] data = new byte[3];
		m_i2c.read(kOffsetRegister, data.length, data);

		// Sensor is little endian... swap bytes
		axes.XAxis = data[0] * kGsPerOffsetLSB;
		axes.YAxis = data[1] * kGsPerOffsetLSB;
		axes.ZAxis = data[2] * kGsPerOffsetLSB;
		return axes;
	}

	public void setOffsets(AllAxes offsets) {
		setOffset(Axes.kX, offsets.XAxis);
		setOffset(Axes.kY, offsets.YAxis);
		setOffset(Axes.kZ, offsets.ZAxis);
	}

	public double getAcceleration() {
		AllAxes axes = getAccelerations();
		return Math.sqrt(axes.XAxis * axes.XAxis + axes.YAxis * axes.YAxis
				+ axes.ZAxis * axes.ZAxis);
	}

	/**
	 * WARNING: May not actually work
	 */
	public void calibrate() {

		ADXL345_I2C.AllAxes reading = getAccelerations();
		double acceleration = getAcceleration();
		// System.out.println("Acc: " + reading.XAxis + "," + reading.YAxis +
		// ","
		// + reading.ZAxis);

		reading.XAxis = reading.XAxis - reading.XAxis / acceleration;
		reading.YAxis = reading.YAxis - reading.YAxis / acceleration;
		reading.ZAxis = reading.ZAxis - reading.ZAxis / acceleration;
		// System.out.println("Offset: " + reading.XAxis + "," + reading.YAxis +
		// ","
		// + reading.ZAxis);
		setOffsets(reading);
	}
}
