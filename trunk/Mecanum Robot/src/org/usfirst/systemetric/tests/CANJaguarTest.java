package org.usfirst.systemetric.tests;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * A program to test which jaguars are connected. Make sure to change the manifest file!
 * 
 * @author Eric
 *
 */
public class CANJaguarTest extends IterativeRobot {
	/** The ID of the {@link CANJaguar} with the smallest ID*/
	public static final int LOWEST_ID = 2;
	
	/** The ID of the {@link CANJaguar} with the largest ID*/
	public static final int HIGHEST_ID = 7;
	

	public void teleopInit() {
		int[] connectedIds = new int[HIGHEST_ID - LOWEST_ID + 1];
		int connectedIdCount = 0;
		int[] failedIds = new int[HIGHEST_ID - LOWEST_ID + 1];
		int failedIdCount = 0;
		for(int id = LOWEST_ID; id <= HIGHEST_ID; id++) {
			try {
				//Check if the jaguar is connected
				new CANJaguar(id, ControlMode.kPercentVbus);
	            
	            //If so, append the id to the array of connections
	            connectedIds[connectedIdCount++] = id;
            } catch (CANTimeoutException e) {
            	//Otherwise, append it to the array of failed connections
            	failedIds[failedIdCount++] = id;
            }
		}
		
		//Display which jaguars are connected
		if(connectedIdCount != 0) {
			StringBuffer b = new StringBuffer("Successfully connected to jaguar");
			
			if(connectedIdCount > 1)
				b.append("s");
			
			for(int i = 0;  i< connectedIdCount; i++)
				b.append(" ").append(connectedIds[i]);
			System.out.println(b);
		}

		//Display which jaguars aren't connected
		if(failedIdCount != 0) {
			StringBuffer b = new StringBuffer("Could not connect to jaguars");
			
			if(failedIdCount > 1)
				b.append("s");
			
			for(int i = 0; i < failedIdCount; i++)
				b.append(" ").append(failedIds[i]);
			System.out.println(b);
		}
		
	}
	
}
