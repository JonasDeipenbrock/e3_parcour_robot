package levelSolver;

import e3base.Movement;
import e3base.UltrasonicPosition;

public class FindAndPush implements ILevelSolver {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		UltrasonicPosition uPosition = UltrasonicPosition.getInstance();
		// Move Ultrasonic Sensor in to upright position
		uPosition.moveUP();
		// Move foreward until distance drops below threshold

		// Move foreward until distance rises over threshold

		// Turn 90 Degrees

		// Move foreward until Motors stop or push sensor

		// turn right

		// move foreward

		// turn left

		// move foreard

		// turn left

		// Push until end

		// turn left

		// move foreward

		// turn left

		// move foreward until blue band
	}

	@Override

	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
