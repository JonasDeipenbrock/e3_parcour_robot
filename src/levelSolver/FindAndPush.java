package levelSolver;

import e3base.Movement;
import e3base.UltrasonicPosition;
import e3base.UltrasonicSensor;
import lejos.utility.Delay;

public class FindAndPush implements ILevelSolver {

	@Override
	public void run() {
		UltrasonicPosition uPosition = UltrasonicPosition.getInstance();
		Movement movement = Movement.getInstance();
		UltrasonicSensor uSensor = UltrasonicSensor.getInstance();

		// Move Ultrasonic Sensor in to upright position
		uPosition.moveUP();
		// Move forward until distance goes above threshold
		movement.forward();
		while (uSensor.getDistance() < 0.3f) {
			Delay.msDelay(10); //TODO: Check if needed
		}
		// Move forward until distance drops bellow threshold again
		while (uSensor.getDistance() > 0.3f) {
			Delay.msDelay(10); //TODO: Check if needed
		}
		movement.stop();
		// Turn 90 Degrees
		movement.turnRight90();
		// TODO: Move forward until Motors stop or push sensor
		movement.forward();
		Delay.msDelay(1000);
		movement.stop();
		// turn right
		movement.turnRight90();
		// move forward
		movement.moveByDistance(10);
		// turn left
		movement.turnLeft90();
		// move forward
		movement.moveByDistance(10);
		// turn left
		movement.turnLeft90();
		// TODO: Push until end
		movement.forward();
		Delay.msDelay(1000);
		movement.stop();
		// turn left
		movement.turnLeft90();
		// move forward
		movement.moveByDistance(10);
		// turn left
		movement.turnLeft90();
		// TODO: move forward until blue band
	}

	@Override

	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
