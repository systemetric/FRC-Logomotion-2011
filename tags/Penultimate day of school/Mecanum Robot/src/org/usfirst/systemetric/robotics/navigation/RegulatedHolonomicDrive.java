package org.usfirst.systemetric.robotics.navigation;

/**
 * An interface for a holonomic drive that regulates its heading.
 * 
 * @author Eric
 *
 */
public interface RegulatedHolonomicDrive extends HolonomicDrive {
	public void setHeading(double heading);
}
