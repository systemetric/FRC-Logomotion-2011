package org.usfirst.systemetric.robotics.navigation;

import org.usfirst.systemetric.geometry.Matrix;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.util.AngleFinder;
import org.usfirst.systemetric.util.HeadingFinder;

import edu.wpi.first.wpilibj.PIDController;

/**
 * Allows a {@link MecanumDrive} to have its heading regulated and maintained by using an {@link AngleFinder}.
 * 
 * @author Eric
 *
 */
public class RegulatedMecanumDrive extends MecanumDrive implements RegulatedHolonomicDrive {
	HeadingFinder finder;
	PIDController controller;
	Vector driveVelocity;
	
	public RegulatedMecanumDrive(Wheel[] wheels, AngleFinder angleFinder) {
	    super(wheels);
	    
	    this.finder = new HeadingFinder(angleFinder);
	    
	    controller = new PIDController(1, 0, 0, finder, (MecanumDrive) this);
	    controller.setInputRange(0, 2*Math.PI);
	    controller.setContinuous();
    }
	
	public void setHeading(double heading) {
		controller.enable();
		controller.setSetpoint(heading);
	}
	
	public void setTurnVelocity(double turnVelocity) {
		controller.disable();
	    super.setTurnVelocity(turnVelocity);
	}
	
	public void setDriveVelocity(Vector driveVelocity) {
		this.driveVelocity = driveVelocity;
	}
	
	protected void update() {
		super.setDriveVelocity(Matrix.fromRotation(-finder.getRadians()).times(driveVelocity));
		super.update();
	}
}
