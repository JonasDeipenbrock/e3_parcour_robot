package levelSolver;

import e3base.Base;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Move;
import wrappers.Movement;
import wrappers.UltrasonicSensor;

public class BridgeCrossing implements ILevelSolver {
	
	private Movement movement;
	UltrasonicSensor usSensor;
	
	public BridgeCrossing() {
		movement = Movement.getInstance();
		usSensor = UltrasonicSensor.getInstance();
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
		float distanceDown = 0.01f;

		//start driving
		movement.forward();
		//as long as sensor distance < 0.1 drive;
		while(distanceDown < 0.18) {
			if(distanceDown > 0.04) {
				//movement.setRotateSpeed(10);
			}
			//get sensor distance
			distanceDown = usSensor.getDistance();
		}
		movement.stop();
		System.out.println(distanceDown);
		//else do again
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
