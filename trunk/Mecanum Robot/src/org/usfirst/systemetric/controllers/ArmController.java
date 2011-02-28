package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.OperatorConsole;
import org.usfirst.systemetric.robotics.Arm;
import org.usfirst.systemetric.robotics.Arm.PegPosition;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class ArmController {
	Arm arm;

	public ArmController(Arm arm) {
		this.arm = arm;
	}

	public void controlWith(OperatorConsole cb) {
		GenericHID joystick = cb.armJoystick;
		PegPosition position = null;
		if (true || joystick.getTrigger()) { //this is the original code
		//if (joystick.getTrigger()) {
			try {
				arm.setSpeed(-joystick.getY());
			} catch (CANTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else {
			if (joystick.getRawButton(11))
				position = PegPosition.BOTTOM;
			else if (joystick.getRawButton(12))
				position = PegPosition.BOTTOM_OFFSET;
			else if (joystick.getRawButton(9))
				position = PegPosition.MIDDLE;
			else if (joystick.getRawButton(10))
				position = PegPosition.MIDDLE_OFFSET;

			/*
			 * if (position != null) try { //arm.moveTo(position); } catch
			 * (CANTimeoutException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
		}
	}
}