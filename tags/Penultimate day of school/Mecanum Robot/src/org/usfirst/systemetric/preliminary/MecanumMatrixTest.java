package org.usfirst.systemetric.preliminary;

import org.usfirst.systemetric.geometry.Matrix;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive.Wheel;
import org.usfirst.systemetric.sensors.LineTracer.Detector;

import com.sun.squawk.util.Comparer;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * A test to check whether the math behind the mecanum drive works correctly.
 * Prints the speed each motor would be run at to System.out
 * 
 * @author Eric
 * 
 */
public class MecanumMatrixTest {

	/**
	 * @param args
	 */

	public static class TestJaguar implements SpeedController {
		String name;

		public TestJaguar(String name) {
			this.name = name;
		}

		public void pidWrite(double output) {
			set(output);
		}

		public double get() {
			// TODO Auto-generated method stub
			return 0;
		}

		public void set(double speed, byte syncGroup) {
			System.out.println(name + ": " + speed + ", " + syncGroup);

		}

		public void set(double speed) {
			System.out.println(name + ": " + speed);

		}

		public void disable() {
			System.out.println(name + ": disabled");

		}

	}

	public static void main(String[] args) {
		Double d = new Double(2);
		
		(new Comparer() {
            public int compare(Object a, Object b) {
            	System.out.println("Aww");
	            return 0;
            }
            public int compare(Double a, Double b) {
            	System.out.println("Good");
	            return (int) Math.ceil(a.doubleValue() - b.doubleValue());
            }
		}).compare(d, d);
		
		
		Wheel[] wheels = new Wheel[4];

		wheels[0] = new Wheel(new Vector(10, 10), new Vector(0, 10),
		    new Vector(1, 1), new TestJaguar("FrontRight"));

		wheels[1] = new Wheel(new Vector(10, -10), new Vector(0, 10),
		    new Vector(1, -1), new TestJaguar(" BackRight"));

		wheels[2] = new Wheel(new Vector(-10, -10), new Vector(0, 10),
		    new Vector(-1, -1), new TestJaguar("  BackLeft"));

		wheels[3] = new Wheel(new Vector(-10, 10), new Vector(0, 10),
		    new Vector(-1, 1), new TestJaguar(" FrontLeft"));

		MecanumDrive d2 = new MecanumDrive(wheels);

		System.out.println("-----------------");
	}
}
