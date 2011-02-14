package org.usfirst.systemetric.robotics;

import org.usfirst.systemetric.robotics.navigation.OrthogonalMecanumDrive;

import edu.wpi.first.wpilibj.parsing.IMechanism;

public class BaseRobot extends OrthogonalMecanumDrive implements IMechanism {
	Arm arm;
	Grabber grabber;	
}
