package org.usfirst.systemetric.tests;

import org.usfirst.systemetric.OperatorConsole;

import org.usfirst.systemetric.controllers.ArmController;
import org.usfirst.systemetric.controllers.GrabberController;
import org.usfirst.systemetric.controllers.MinibotController;
import org.usfirst.systemetric.controllers.StrafeDriveController;
import org.usfirst.systemetric.robotics.Arm;
import org.usfirst.systemetric.robotics.BaseRobot;
import org.usfirst.systemetric.robotics.Grabber;
import org.usfirst.systemetric.robotics.Minibot;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * Class to test most of the functions of the robot
 * 
 * @author Eric, Louis
 * 
 */

public class DrivableRobotTest extends IterativeRobot {
	BaseRobot r = BaseRobot.getInstance();
	
	//create some sensible names for the various channels
	private static final int GRABBER_GRAB_SOLENOID_CHANNEL = 1;
	private static int GRABBER_TILT_SOLENOID_CHANNEL = 3;
	private static final int MINIBOT_DEPLOYMENT_SOLENOID_CHANNEL = 2;
	private static final int ARM_CAN_ID = 7;
	private static final int COMPRESSOR_PRESSURE_SWITCH_CHANNEL  = 1;
	private static final int COMPRESSOR_RELAY_CHANNEL = 1;
	
	private boolean deploymentIsUp = false;


	Timer time;
	
	Compressor c = new Compressor(COMPRESSOR_PRESSURE_SWITCH_CHANNEL, COMPRESSOR_RELAY_CHANNEL);	
	
	Grabber g = new Grabber(GRABBER_TILT_SOLENOID_CHANNEL, GRABBER_GRAB_SOLENOID_CHANNEL);
	GrabberController gc = new GrabberController(g);
	
	MecanumDrive m = OrthogonalMecanumDriveFactory.DEFAULT_ROBOT;
	StrafeDriveController mc = new StrafeDriveController(m);

	Arm a = new Arm(ARM_CAN_ID);
	ArmController ac = new ArmController(a);
	
	Minibot minibot = new Minibot(MINIBOT_DEPLOYMENT_SOLENOID_CHANNEL);
	MinibotController minibotController = new MinibotController(minibot); // minibot Controller
	
	//added:
	//HiTechnicCompass testCompass;
	
	public void teleopInit() {
	    c.start();
	    //time.start();
	    //added
	    //testCompass = new HiTechnicCompass(4);
	}
	
	public void disabledContinuous(){
		if (!deploymentIsUp) {
			minibot.bringDeploymentUp();
		}
	}
	
	
	public void teleopPeriodic() {
		try {
			OperatorConsole ob = OperatorConsole.getInstance();
	        gc.controlWith(ob);
	        ac.controlWith(ob);
	        mc.controlWith(ob);
	        minibotController.controlWith(ob);
	        //minibotController.updateTime(time.get());
	        
	        
	        //added
	        //System.out.println(testCompass.getAngle());
        } catch (EnhancedIOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
}
