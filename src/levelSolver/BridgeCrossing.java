package levelSolver;

import e3base.Base;
import e3base.Movement;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Move;

public class BridgeCrossing implements ILevelSolver {
	
	private Movement movement;
	EV3UltrasonicSensor usSensor;
	
	public BridgeCrossing() {
		movement = Movement.getInstance();
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
		movement.forward();
		//as long as sensor distance < 0.1 drive;
		while(distanceDown[0] < 0.18) {
			if(distanceDown[0] > 0.04) {
				//movement.setRotateSpeed(10);
			}
			//get sensor distance
			usSensor.fetchSample(distanceDown, 0);
		}
		movement.stop();
		System.out.println(distanceDown[0]);
		//else do again
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
