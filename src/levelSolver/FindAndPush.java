package levelSolver;

import e3base.Base;
import e3base.Movement;
import e3base.UltrasonicPosition;
import lejos.robotics.navigation.Move;
import lejos.utility.Delay;

public class FindAndPush implements ILevelSolver {

	@Override
	public void run() {
		UltrasonicPosition uPosition = UltrasonicPosition.getInstance();
		Movement movement = Movement.getInstance();
		// Move Ultrasonic Sensor in to upright position
		uPosition.moveUP();
		// Move foreward until distance drops below threshold
		movement.forward();
		// TODO: read sensor
		Delay.msDelay(1000);
		
		movement.stop();
		// Move foreward until distance rises over threshold
		movement.forward();
		// TODO: read sensor
		Delay.msDelay(1000);
		movement.stop();
		// Turn 90 Degrees
		movement.turnRight90();
		// Move foreward until Motors stop or push sensor

		// turn right
		movement.turnRight90();
		// move foreward
		movement.moveByDistance(10);
		// turn left
		movement.turnLeft90();
		// move foreward
		movement.moveByDistance(10);
		// turn left
		movement.turnLeft90();
		// Push until end

		// turn left
		movement.turnLeft90();
		// move foreward
		movement.moveByDistance(10);
		// turn left
		movement.turnLeft90();
		// move foreward until blue band
	}

	@Override

	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
