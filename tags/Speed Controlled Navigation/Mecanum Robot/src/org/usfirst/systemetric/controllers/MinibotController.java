package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.ControlBoard;

public class MinibotController implements Controllable {
	public void controlWith(ControlBoard cb) {
		if(cb.minibitDeploy.get())
			;//deploy minibot
	}

}
