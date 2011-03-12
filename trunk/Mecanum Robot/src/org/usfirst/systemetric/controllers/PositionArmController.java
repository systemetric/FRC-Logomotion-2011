package org.usfirst.systemetric.controllers;


import org.usfirst.systemetric.BaseRobot;
import org.usfirst.systemetric.OperatorConsole;
import org.usfirst.systemetric.robotics.PositionControlledArm;
import org.usfirst.systemetric.robotics.PositionControlledArm.PegPosition;
import org.usfirst.systemetric.util.DeadZone;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class PositionArmController implements Controller {
	final double MANUAL_SPEED = 0.5;
	
	PositionControlledArm arm;
	DeadZone              deadZone       = new DeadZone(0.05);
	long                  lastUpdateTime = -1;

	public PositionArmController(PositionControlledArm arm) {
		this.arm = arm;
	}

	public PositionArmController(BaseRobot robot) {
	    this(robot.arm);
    }

	public void controlWith(OperatorConsole cb) {
		//Get time since last update
		long currentTime = System.currentTimeMillis();
		double dt = (lastUpdateTime == -1 ? 0 : lastUpdateTime - currentTime) / 1000.0;
		lastUpdateTime = currentTime;
		
		GenericHID joystick = cb.armJoystick;
		
		PegPosition target = arm.getTarget();

		//Go to a specified height if a button is pressed
		if (joystick.getRawButton(9))
			target = PegPosition.BOTTOM;
		else if (joystick.getRawButton(8))
			target = PegPosition.BOTTOM_OFFSET;
		else if (joystick.getRawButton(7))
			target = PegPosition.MIDDLE;
		else if (joystick.getRawButton(6))
			target = PegPosition.MIDDLE_OFFSET;
		else if (joystick.getRawButton(10))
			target = PegPosition.RESET;
		
		//Manually adjust the height by using the joystick
		double speed = deadZone.applyTo(joystick.getY());
		if(target != null) target = PegPosition.custom(target.height + speed * dt * MANUAL_SPEED);
		System.out.println("Target: " + target);
		
		//Turn on the motor
		try {
			arm.moveTo(target);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
	}
}