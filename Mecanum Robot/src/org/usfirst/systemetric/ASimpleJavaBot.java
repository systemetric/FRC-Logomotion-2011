package org.usfirst.systemetric;


import org.usfirst.systemetric.robotics.MecanumWheel;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class ASimpleJavaBot extends SimpleRobot {
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
	
	DriverStationLCD lcd = DriverStationLCD.getInstance();
	MecanumWheel wheel;
	
	protected DigitalInput digitalinput;
	
    public void autonomous() {
    	lcd.println(Line.kUser2, 1, "Hello Java World!");
    	lcd.updateLCD();
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
    	lcd.println(Line.kUser2, 1, "Hello Java World!");
    	lcd.updateLCD();

    }
}
