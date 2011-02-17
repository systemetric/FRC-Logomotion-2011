package org.usfirst.systemetric.preliminary;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;

public class CompressorTest extends IterativeRobot {
	Compressor c;
	Relay r;
	
	
	public void robotInit() {
		c = new Compressor(1, 1);
	}
}
