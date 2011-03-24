package org.usfirst.systemetric.preliminary;

import org.usfirst.systemetric.OperatorConsole;

import edu.wpi.first.wpilibj.IterativeRobot;

public class CompassTest extends IterativeRobot{
	//HiTechnicCompass testCompass;
	OperatorConsole cb;
	//int i;
	
	public void robotInit() {
		//testCompass = new HiTechnicCompass(4);
		System.out.println("RobotInit() completed.\n");
		//i = new Integer(0);
		
	}
	
	public void teleopInit() {
		System.out.println("hi from the robot");
	}
	
	public void teleopPeriodic() {
		//System.out.println(testCompass.getAngle());
		//i++;
		//System.out.println(i);
	}

}