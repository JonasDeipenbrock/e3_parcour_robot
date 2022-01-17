package levelSolver;

import drivingConditions.BumperCondition;
import drivingConditions.ButtonCondition;
import drivingConditions.ComparisonMethod;
import drivingConditions.LinesPassedCondition;
import drivingConditions.OrCondition;
import drivingConditions.TimeoutCondition;
import drivingConditions.UltrasonicCondition;
import e3base.Helper;
import lejos.utility.Delay;
import wrappers.ColorSensor;
import wrappers.ExitCode;
import wrappers.LEDPattern;
import wrappers.Movement;
import wrappers.StatusIndicator;
import wrappers.UltrasonicPosition;
import wrappers.UltrasonicSensor;

public class FindAndPush implements ILevelSolver {

	@Override
	public ExitCode run() {
		Movement movement = Movement.getInstance();
		UltrasonicSensor uSensor = UltrasonicSensor.getInstance();
		ColorSensor colorSensor = ColorSensor.getInstance();
		Helper helper = Helper.getInstance();
		StatusIndicator ind = StatusIndicator.getInstance();
		UltrasonicPosition uPosition = UltrasonicPosition.getInstance();

		uPosition.moveUP();

		ind.setLED(LEDPattern.LED_RED);

		movement.turnLeft90();
		movement.forwardUntil(new OrCondition(new BumperCondition(),
				                              new ButtonCondition()));
		movement.moveByDistance(1);
		movement.moveByDistance(-10);
		movement.turnRight90();
		movement.setToMaxSpeed();
		movement.forwardUntil(new OrCondition(new UltrasonicCondition(ComparisonMethod.GREATER, 0.4f),
		                                      new TimeoutCondition(2000)));
		ind.setLED(LEDPattern.LED_ORANGE);

		System.out.println("Moving to box");
		movement.forwardUntil(new OrCondition(new UltrasonicCondition(ComparisonMethod.LESS, 0.4f),
				                              new ButtonCondition()));

		ind.setLED(LEDPattern.LED_ORANGE);
		// move a bit further -> in front of box
		movement.moveByDistance(10);
		movement.turnRight90();
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
		movement.moveByDistance(-2);
		movement.turnRight90();
		movement.moveByDistance(20);
		movement.turnLeft90();
		movement.moveByDistance(15);
		movement.turnLeft90();

		// Move box in corner
		movement.forwardUntil(new OrCondition(new TimeoutCondition(3000),
				                              new LinesPassedCondition(1),
				                              new ButtonCondition()));

		ind.setLED(LEDPattern.LED_RED_FLASH);
		// Drive to exit
		movement.moveByDistance(-10);
		movement.turnLeft90();

		// shortly move backwards to ensure alignment
		movement.backward();
		Delay.msDelay(100);
		movement.stop();

		movement.forwardUntil(new OrCondition(new LinesPassedCondition(1),
				                              new ButtonCondition()));

		movement.turnLeft90();
		ind.setLED(LEDPattern.LED_GREEN);

		// ausrichtung an wand -> 50cm ideal
		movement.forward();
		while (!colorSensor.checkBlue()) { // stop on blue line
			if (!helper.checkLoop(false, false))
				break;
			float distanceRight = uSensor.getDistance();
			// we want to reach 0.5m to the right ideally
			float offset = (0.4f - distanceRight) * -500; // error is difference from ideal wall point
			movement.setMotorRotation(offset, 500f);
			movement.forward();
		}
		movement.stopCorrected();
		ind.setLED(LEDPattern.LED_RED);
		return ExitCode.SUCCESSFULL;
	}
}
