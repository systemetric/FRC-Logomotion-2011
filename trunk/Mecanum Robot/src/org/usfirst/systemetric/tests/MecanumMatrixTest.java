package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.geometry.Matrix;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive.Wheel;

import edu.wpi.first.wpilibj.SpeedController;

import static java.lang.Math.*;

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
		Wheel[] wheels = new Wheel[4];
		
		wheels[0] = new Wheel(new Vector (10,10), new Vector (0,10), new Vector (1,1),
		new TestJaguar("FrontRight"));

		wheels[1] = new Wheel(new Vector (10,-10), new Vector (0,10), new Vector (1,-1),
		new TestJaguar(" BackRight"));

		wheels[2] = new Wheel(new Vector (-10,-10), new Vector (0,10), new Vector (-1,-1),
		new TestJaguar("  BackLeft"));

		wheels[3] = new Wheel(new Vector (-10,10), new Vector (0,10), new Vector (-1,1),
		new TestJaguar(" FrontLeft"));
		
		MecanumDrive d = new MecanumDrive(wheels);
		
		d.setTurnVelocity(90);
		System.out.println("-----------------");
	}
}
