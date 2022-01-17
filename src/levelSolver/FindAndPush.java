package levelSolver;


import drivingConditions.BlueStripCondition;
import drivingConditions.BumperCondition;
import drivingConditions.ButtonCondition;
import drivingConditions.ComparisonMethod;
import drivingConditions.LinesPassedCondition;
import drivingConditions.OrCondition;
import drivingConditions.TimeoutCondition;
import drivingConditions.UltrasonicCondition;
import wrappers.ExitCode;
import wrappers.LEDPattern;
import wrappers.Movement;
import wrappers.StatusIndicator;
import wrappers.UltrasonicPosition;

public class FindAndPush implements ILevelSolver {

	@Override
	public ExitCode run() {
		Movement movement = Movement.getInstance();
		StatusIndicator ind = StatusIndicator.getInstance();
		UltrasonicPosition uPosition = UltrasonicPosition.getInstance();

		uPosition.moveUP();

		ind.setLED(LEDPattern.LED_RED);
		movement.moveByDistance(20);
		movement.turnLeft90();
		movement.forwardUntil(new OrCondition(new BumperCondition(),
				                              new ButtonCondition()));
		movement.moveByDistance(2);
		movement.moveByDistance(-7);
		movement.turnRight90();
		movement.setToMaxSpeed();
		movement.forwardUntil(new UltrasonicCondition(ComparisonMethod.GREATER, 0.6f));
		ind.setLED(LEDPattern.LED_ORANGE);

		System.out.println("Moving to box");
		movement.forwardUntil(new OrCondition(new UltrasonicCondition(ComparisonMethod.LESS, 0.4f),
				                              new ButtonCondition()));

		ind.setLED(LEDPattern.LED_RED);
		// move a bit further -> in front of box
		movement.moveByDistance(20);
		movement.turnRight90();
		movement.moveByDistance(-12);
		System.out.println("Pushing the box");

		ind.setLED(LEDPattern.LED_GREEN_FLASH);
		// Move forward until Motors stop or push sensor -> Push box against wall
		movement.forwardUntil(new OrCondition(new TimeoutCondition(4000),
				                              new LinesPassedCondition(2),
				                              new ButtonCondition()));
		movement.moveByDistance(15);
		System.out.println("At wall");

		ind.setLED(LEDPattern.LED_ORANGE_FLASH);
		// Move around box by one side
		movement.moveByDistance(-4);
		movement.turnRight90();
		movement.moveByDistance(20);
		movement.turnLeft90();
		movement.forwardUntil(new BumperCondition());
		movement.moveByDistance(6);
		movement.moveByDistance(-4);
		movement.turnLeft90();

		// Move box in corner
		movement.forwardUntil(new OrCondition(new TimeoutCondition(4000),
				                              new LinesPassedCondition(1),
				                              new ButtonCondition()));

		ind.setLED(LEDPattern.LED_RED_FLASH);
		// Drive to exit
		movement.moveByDistance(-10);
		movement.turnLeft90();
		movement.moveByDistance(-12);
		movement.moveByDistance(4);
		movement.turnLeft90();


		movement.forwardUntil(new BumperCondition());
		movement.moveByDistance(2);
		movement.moveByDistance(-4);

		movement.turnLeft90();
		movement.moveByDistance(10);
		movement.backward();
		movement.waitUntil(new UltrasonicCondition(ComparisonMethod.GREATER, 0.15f));
		movement.stopCorrected();
		movement.turnRight90();
		ind.setLED(LEDPattern.LED_GREEN);
		movement.forwardUntil(new OrCondition(new BlueStripCondition(),
				                              new TimeoutCondition(1000)));

		return ExitCode.SUCCESSFULL;
	}
}
