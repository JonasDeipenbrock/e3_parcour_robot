package levelSolver;

import e3base.Base;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class BridgeCrossing implements ILevelSolver {
	
	DifferentialPilot robot;
	EV3UltrasonicSensor usSensor;
	
	public BridgeCrossing(DifferentialPilot pilot) {
		robot = pilot;
		usSensor = Base.usSensor;
	}

	@Override
	public void run() {
//		do {
			System.out.println("Starting bridge crossing");
			toCorner();
			//while not on edge => drive forward
			
			//while on edge => turn
			
			//while not on edge => drive forward
			
			//while on edge => turn
			
			//drive until blue line
//		} while (Button.ENTER.isUp());
	}
	
	/**
	 * Drives to the next corner and stops there
	 */
	void toCorner() {
		float[] distanceDown = {0.01f};

		//start driving
		robot.forward();
		//as long as sensor distance < 0.1 drive;
		while(distanceDown[0] < 0.18) {
			if(distanceDown[0] > 0.04) {
				robot.setRotateSpeed(10);
			}
			//get sensor distance
			usSensor.fetchSample(distanceDown, 0);
		}
		robot.stop();
		System.out.println(distanceDown[0]);
		//else do again
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
