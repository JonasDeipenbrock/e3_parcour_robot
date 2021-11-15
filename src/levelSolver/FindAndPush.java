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
			Delay.msDelay(100); //TODO: Check if needed
		}
		// Move forward until distance drops bellow threshold again
		while (uSensor.getDistance() > 0.3f) {
			Delay.msDelay(100); //TODO: Check if needed
		}
		//move a bit further
		Delay.msDelay(500);
		movement.stop();
		// Turn 90 Degrees
		movement.turnRight90();
		// TODO: Move forward until Motors stop or push sensor
		movement.forward();
		Delay.msDelay(2000);
		movement.stop();
		// turn right
		movement.turnRight90();
		// move forward
		movement.moveByDistance(20);
		// turn left
		movement.turnLeft90();
		// move forward
		movement.moveByDistance(20);
		// turn left
		movement.turnLeft90();
		// TODO: Push until end
		movement.forward();
		Delay.msDelay(2000);
		movement.stop();
		// turn left
		movement.turnLeft90();
		// move forward
		movement.moveByDistance(20);
		// turn left
		movement.turnLeft90();
		// TODO: move forward until blue band
		movement.forward();
		Delay.msDelay(2000);
		movement.stop();
	}

	@Override

	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
