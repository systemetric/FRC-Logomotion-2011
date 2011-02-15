package org.usfirst.systemetric.robotics;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.parsing.IMechanism;

public class Arm implements IMechanism {
	public static class PegPosition {
		final int BOTTOM = 1;
		final int BOTTOM_OFFSET = 2;
		final int MIDDLE = 3;
		final int MIDDLE_OFFSET = 4;
		final int TOP_OFFSET = 6;
	}

	Encoder encoder;
	SpeedController motors;

	DigitalInput bottomLimit;
	DigitalInput topLimit;
	
	PIDController x;
	
    java.util.Timer m_controlLoop = new Timer();
    
    boolean canGoUp = true;
    boolean canGoDown = true;

	public Arm(Encoder encoder, SpeedController leftMotor,
			SpeedController rightMotor, DigitalInput bottomLimit,
			DigitalInput topLimit) {
		
		motors = new Motors(leftMotor, rightMotor);
		m_controlLoop.schedule(new ArmSafetyTask(), 0, 20);
		
		this.encoder = encoder;
		this.bottomLimit = bottomLimit;
		this.topLimit = topLimit;
	}
	
	public void setSpeed(double speed) {
		if(speed > 0 && canGoUp || speed < 0 && canGoDown) {
			motors.set(speed);
			//System.out.println("Going at "+speed);
		}
		else {
			motors.disable();
			//System.out.println("Illegal!");
		}
		
	}

	public void goToHeight(int encoderCount) {

	}

	private class Motors implements SpeedController, IMechanism {
		SpeedController leftMotor;
		SpeedController rightMotor;

		public Motors(SpeedController leftMotor, SpeedController rightMotor) {
			this.leftMotor = leftMotor;
			this.rightMotor = rightMotor;
		}

		public void pidWrite(double output) {
			leftMotor.pidWrite(output);
			rightMotor.pidWrite(output);
		}

		public double get() {
			return (leftMotor.get() + rightMotor.get()) / 2;
		}

		public void set(double speed, byte syncGroup) {
			leftMotor.set(speed, syncGroup);
			rightMotor.set(speed, syncGroup);

		}

		public void set(double speed) {
			leftMotor.set(speed);
			rightMotor.set(speed);

		}

		public void disable() {
			leftMotor.disable();
			rightMotor.disable();

		}

	}

	private class ArmSafetyTask extends TimerTask {
		public void run() {
			canGoDown = !bottomLimit.get();
			canGoUp = !topLimit.get();
			System.out.println("Down:"+canGoDown+", Up:"+canGoUp);
			
			if(!canGoDown && motors.get() < 0) {
				//Motor is going down
				motors.disable();
				//encoder.reset();
			}
			if(!canGoUp && motors.get() > 0) {
				//Motor is going up
				motors.disable();
			}
		}
	}

}
