package org.usfirst.systemetric;

import org.usfirst.systemetric.controllers.ArmController;
import org.usfirst.systemetric.controllers.Controller;
import org.usfirst.systemetric.controllers.GrabberController;
import org.usfirst.systemetric.controllers.MinibotController;
import org.usfirst.systemetric.controllers.PositionArmController;
import org.usfirst.systemetric.controllers.StrafeDriveController;
import org.usfirst.systemetric.robotics.MinibotDeployer;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.SmartDashboard;

/**
 * Test program to run at the weekend
 * 
 * @author Eric
 * 
 */
public class MainDemo extends IterativeRobot {

	static final double         TEAM_SPEED   = 1.5;
	static final double         PUBLIC_SPEED = 0.5;

	final BaseRobot             robot        = BaseRobot.getInstance();
	
	final StrafeDriveController dc           = new StrafeDriveController(robot,
	                                             PUBLIC_SPEED);
	final PositionArmController ac           = new PositionArmController(robot);
	final GrabberController     gc           = new GrabberController(robot);
	final Controller[]          controllers  = new Controller[] {gc, ac, dc};
	GenericHID                  joystick;
	OperatorConsole oc;
	

	MinibotDeployer    minibot           = new MinibotDeployer(2);
	MinibotController  minibotController = new MinibotController(minibot);

	public boolean getAndCheckPassword() {
		// Buttons we care about
		final int first_button = 9;
		final int last_button = 12;

		// Sequence we want
		int[] password = {9, 12, 10, 11};

		// Fetch the joystick
		try {
			joystick = OperatorConsole.getInstance().driveJoystick;
		} catch (EnhancedIOException e) {
			e.printStackTrace();
		}

		// Iterate over each character of the password
		for (int passwordIdx = 0; passwordIdx < password.length; passwordIdx++) {
			int passwordChar = password[passwordIdx];
			System.out.println("\tYou should push " + passwordChar);
			// Keep checking until buttons are pushed
			boolean noButtons = true;
			while (noButtons) {

				// For each button
				for (int button = first_button; button <= last_button; button++) {
					if (joystick.getRawButton(button)) {
						System.out.println("\t\tYou pushed " + button);

						// incorrect button pushed
						if (passwordChar != button)
							return false;
						// correct button pushed - stop checking at end of loop
						else
							noButtons = false;
					}
				}
			}
		}

		return true;
	}

	public void autonomousInitSTUPIDSTUPIDSTUPID() {
		/* Team member has entered password */
		/* TODO: test */
		System.out.println("Password:");
		if (getAndCheckPassword()) {
			dc.setSpeed(TEAM_SPEED);
			System.out.println("Password Accepted!");
		} else {
			dc.setSpeed(PUBLIC_SPEED);
			System.out.println("Password Denied");
		}
	}

	public void teleopInit() {
		SmartDashboard.init();
		
		try {
			oc = OperatorConsole.getInstance();
		} catch (EnhancedIOException e) {
			e.printStackTrace();
		}

		if (oc.driveJoystick.getTrigger() && oc.armJoystick.getTrigger()) {
			dc.setSpeed(TEAM_SPEED);
			SmartDashboard.log(true, "Team mode");
		} else {
			dc.setSpeed(PUBLIC_SPEED);
			SmartDashboard.log(false, "Team mode");
		}

		robot.compressor.start();
	}

	public void teleopPeriodic() {
		try {
			OperatorConsole oc = OperatorConsole.getInstance();

			for (int i = 0; i < controllers.length; i++) {
				controllers[i].controlWith(oc);
			}

			minibotController.controlWith(oc);

		} catch (EnhancedIOException e) {
			e.printStackTrace();
		}
	}
}
