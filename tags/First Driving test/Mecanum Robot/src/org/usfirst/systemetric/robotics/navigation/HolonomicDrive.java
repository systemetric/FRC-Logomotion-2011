package org.usfirst.systemetric.robotics.navigation;

import org.usfirst.systemetric.geometry.Vector;

import edu.wpi.first.wpilibj.parsing.IMechanism;

/**
 *
 * @author Eric
 */
public interface HolonomicDrive extends IMechanism{
	public void setDriveVelocity(Vector driveVelocity);
	public void setTurnVelocity(double turnVelocity);
}
