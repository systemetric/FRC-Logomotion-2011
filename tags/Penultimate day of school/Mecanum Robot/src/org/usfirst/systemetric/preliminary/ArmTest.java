package org.usfirst.systemetric.preliminary;

import org.usfirst.systemetric.OperatorConsole;
import org.usfirst.systemetric.controllers.ArmController;
import org.usfirst.systemetric.robotics.Arm;

import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class ArmTest extends IterativeRobot{
	ArmController armController;
	Arm arm;
	
	public void robotInit() {
			arm = new Arm(6);
			armController = new ArmController(arm);
	}
	
	public void teleopPeriodic() {
		try {
			armController.controlWith(OperatorConsole.getInstance());
	        //System.out.println(arm.getHeight());
			System.out.println(arm.jag.getOutputVoltage() + ", " + arm.jag.getOutputCurrent());
	        
        } catch (Exception e) {
        	
        	
        	
	        e.printStackTrace();
        }
	}
    
}
