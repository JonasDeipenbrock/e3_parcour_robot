package levelSolver;

import lejos.hardware.Button;
import lejos.utility.Delay;
import wrappers.ColorSensor;
import wrappers.Movement;
import wrappers.UltrasonicPosition;
import wrappers.UltrasonicSensor;

public class FindAndPush implements ILevelSolver {

	@Override
	public void run() {
		UltrasonicPosition uPosition = UltrasonicPosition.getInstance();
		Movement movement = Movement.getInstance();
		UltrasonicSensor uSensor = UltrasonicSensor.getInstance();
		ColorSensor colorSensor = ColorSensor.getInstance();

		// Move Ultrasonic Sensor in to upright position
		uPosition.moveUP();
		// Move forward until distance goes above threshold
		movement.forward();
		while (uSensor.getDistance() < 0.3f) {
			if (Button.ENTER.isDown()) return;
			Delay.msDelay(100); //TODO: Check if needed
		}
		// Move forward until distance drops bellow threshold again
		Delay.msDelay(100);
		while (uSensor.getDistance() > 0.3f) {
			if (Button.ENTER.isDown()) return;
			Delay.msDelay(100); //TODO: Check if needed
		}
		//move a bit further
		Delay.msDelay(500);
		movement.stop();
		// Turn 90 Degrees
		movement.turnRight90();
		// Move forward until Motors stop or push sensor
		movement.forward();
		while (!movement.motorStalles()) {
			if (Button.ENTER.isDown()) return;
			Delay.msDelay(100); //TODO: Check if needed
		}
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
		// Push until end
		movement.forward();
		while (!movement.motorStalles()) {
			if (Button.ENTER.isDown()) return;
			Delay.msDelay(100); //TODO: Check if needed
		}
		movement.stop();
		// turn left
		movement.turnLeft90();
		// move forward
		movement.moveByDistance(20);
		// turn left
		movement.turnLeft90();
		// move forward until blue band
		movement.forward();
		while (!colorSensor.checkBlue()) {
			if (Button.ENTER.isDown()) return;
			Delay.msDelay(100); //TODO: Check if needed
		}
		movement.stop();
	}

	@Override

	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
