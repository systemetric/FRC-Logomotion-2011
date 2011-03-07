package org.usfirst.systemetric;

import org.usfirst.systemetric.controllers.ArmController;
import org.usfirst.systemetric.controllers.Controller;
import org.usfirst.systemetric.controllers.GrabberController;
import org.usfirst.systemetric.controllers.MinibotController;
import org.usfirst.systemetric.controllers.StrafeDriveController;
import org.usfirst.systemetric.robotics.Minibot;

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
		                                       new ArmController(robot),
	 	                                       new GrabberController(robot),
		                                       new StrafeDriveController(robot)
	                                       };

	Minibot            minibot           = new Minibot(2);
	MinibotController  minibotController = new MinibotController(minibot);

	public void teleopInit() {
		robot.compressor.start();
		// time.start();
		// added
		// testCompass = new HiTechnicCompass(4);
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
			minibot.bringDeploymentUp();
	}
	
}
