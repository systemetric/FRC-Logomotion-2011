package org.usfirst.systemetric;

import org.usfirst.systemetric.controllers.ArmController;
import org.usfirst.systemetric.controllers.Controller;
import org.usfirst.systemetric.controllers.GrabberController;
import org.usfirst.systemetric.controllers.MinibotController;
import org.usfirst.systemetric.controllers.PositionArmController;
import org.usfirst.systemetric.controllers.StrafeDriveController;
import org.usfirst.systemetric.robotics.MinibotDeployer;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;

/**
 * Test program to use simplified API. May or may not work.
 * 
 * @author Eric
 * 
 */
public class MainV2 extends IterativeRobot {

	final BaseRobot    robot             = BaseRobot.getInstance();

	final Controller[] controllers       = new Controller[] {
	                                           new PositionArmController(robot),
	                                           new GrabberController(robot),
	                                           new StrafeDriveController(robot)
	                                       };

	MinibotDeployer    minibot           = new MinibotDeployer(2);
	MinibotController  minibotController = new MinibotController(minibot);

	AutonomousMode     auto              = new AutonomousMode(robot);

	public void autonomousInit() {
		robot.compressor.start();
		auto.init();
	}

	public void autonomousContinuous() {
		auto.continuous();
	}

	public void teleopInit() {
		auto.disable();
		robot.compressor.start();
	}

	public void teleopPeriodic() {
		try {
			OperatorConsole ob = OperatorConsole.getInstance();

			for (int i = 0; i < controllers.length; i++)
				controllers[i].controlWith(ob);

			minibotController.controlWith(ob);
			// minibotController.updateTime(time.get());

			// added
			// System.out.println(testCompass.getAngle());
		} catch (EnhancedIOException e) {
			e.printStackTrace();
		}
	}

	public void disabledInit() {
		minibot.retract();
	}

}
