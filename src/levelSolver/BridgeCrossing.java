package levelSolver;

import drivingConditions.ComparisonMethod;
import drivingConditions.UltrasonicCondition;
import lejos.hardware.Button;
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

			movement.moveByDistance(15);
			rightCurveAlgo();
			//toCorner();
			//toCorner();
			//while not on edge => drive forward
			
			//while on edge => turn
			
			//while not on edge => drive forward
			
			//while on edge => turn
			
			//drive until blue line
//		} while (Button.ENTER.isUp());
	}


	public void rightCurveAlgo() {
		while (Button.ENTER.isUp()) {
			movement.setMotorRotation(50, 300);
			movement.forward();
			movement.waitUntil(new UltrasonicCondition(ComparisonMethod.GREATER, 0.07f));
			movement.stop();
			System.out.println(usSensor.getDistance());
			if (usSensor.getDistance() >= 1000) {
				System.out.println("Infinity");
				break;
			}
			movement.moveByDistance(-5);
			movement.setSpeed(400f);
			movement.turnLeft();
			movement.waitUntil(new UltrasonicCondition(ComparisonMethod.LESS, 0.06f));
			movement.stop();
			movement.turn(30);
		}
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
