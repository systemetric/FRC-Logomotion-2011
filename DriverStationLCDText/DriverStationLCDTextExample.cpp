
#include "WPILib.h"

/**
 * This example shows how you can write text to the LCD on the driver station.
 */ 
class DriverStationLCDTextExample : public SimpleRobot
{
	Gyro gyro;

public:
	DriverStationLCDTextExample(void) : gyro(1)
	{
	}

	void RobotMain()
	{
		DriverStationLCD *dsLCD = DriverStationLCD::GetInstance();
		while(true) {
			float angle = gyro.GetAngle();
			dsLCD->Printf(DriverStationLCD::kUser_Line1, 1, "Angle: %4.1f", angle);
			dsLCD->UpdateLCD();
			Wait(0.1);
		}
	}
};

START_ROBOT_CLASS(DriverStationLCDTextExample);

