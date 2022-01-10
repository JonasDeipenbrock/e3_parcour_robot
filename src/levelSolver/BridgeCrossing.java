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
			System.out.println("Starting bridge crossing");

			//drive right curve and correct when ip sensor sees ravine
			movement.moveByDistance(15);
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
}
