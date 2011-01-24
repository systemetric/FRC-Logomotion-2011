package org.usfirst.systemetric.robotics;

import org.usfirst.systemetric.geometry.Vector;

/**
 *
 * @author Eric
 */
public interface HolonomicDrive {
    public void setDirection(Vector directionVector);
    public void setOrientation(float heading);
}
