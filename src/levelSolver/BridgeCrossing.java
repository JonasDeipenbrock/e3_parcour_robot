package levelSolver;

import drivingConditions.ComparisonMethod;
import drivingConditions.UltrasonicCondition;
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
		movement.setSpeed(200f);
		//start driving as long as sensor distance < 0.18
		movement.forwardUntil(new UltrasonicCondition(ComparisonMethod.GREATER, 0.18f));
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
