package org.usfirst.systemetric.robotics;

import org.usfirst.systemetric.robotics.navigation.SimpleOrthogonalMecanumDrive;

import edu.wpi.first.wpilibj.parsing.IMechanism;

public class BaseRobot extends SimpleOrthogonalMecanumDrive implements IMechanism {
	Arm arm;
	Grabber grabber;	
}
