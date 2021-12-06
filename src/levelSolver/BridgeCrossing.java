package levelSolver;

import e3base.Base;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Move;
import lejos.utility.Delay;
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
		movement.setSpeed(200f, 1f);
		//start driving
		movement.forward();
		//as long as sensor distance < 0.18 drive;
		Delay.msDelay(3000);
		while(distanceDown < 0.18) {
			//detection doesnt work yet
//			if(distanceDown > 0.04) {
//				movement.setSpeed(150f, 1f);
//			}
			distanceDown = usSensor.getDistance();
		}
		movement.stop();
		movement.moveByDistance(-5);
		movement.turnLeft90();
		System.out.println(distanceDown);
		//else do again
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
