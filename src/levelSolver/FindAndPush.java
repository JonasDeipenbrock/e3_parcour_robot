package levelSolver;


import drivingConditions.BlueStripCondition;
import drivingConditions.BumperCondition;
import drivingConditions.ComparisonMethod;
import drivingConditions.LinesPassedCondition;
import drivingConditions.OrCondition;
import drivingConditions.TimeoutCondition;
import drivingConditions.UltrasonicCondition;
import lejos.hardware.Audio;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import wrappers.ExitCode;
import wrappers.LEDPattern;
import wrappers.Movement;
import wrappers.StatusIndicator;
import wrappers.UltrasonicPosition;

public class FindAndPush implements ILevelSolver {

	Audio audio;

	@Override
	public ExitCode run() {
		Movement movement = Movement.getInstance();
		StatusIndicator ind = StatusIndicator.getInstance();
		UltrasonicPosition uPosition = UltrasonicPosition.getInstance();
		int status;
		audio = LocalEV3.get().getAudio();
		

		uPosition.moveUP();
		movement.setToMaxAcc();
		movement.setToMaxSpeed();

		ind.setLED(LEDPattern.LED_RED);
		movement.moveByDistance(20);
		movement.turnLeft90();
		status = movement.forwardUntil(new BumperCondition());
		if (status == 99) return ExitCode.USER_INTERRUPT;

		movement.moveByDistance(2);
		movement.moveByDistance(-6);
		movement.turnRight90();
		status = movement.forwardUntil(new UltrasonicCondition(ComparisonMethod.GREATER, 0.6f));
		if (status == 99) return ExitCode.USER_INTERRUPT;

		ind.setLED(LEDPattern.LED_ORANGE);

		status = movement.forwardUntil(new UltrasonicCondition(ComparisonMethod.LESS, 0.4f));
		if (status == 99) return ExitCode.USER_INTERRUPT;

		ind.setLED(LEDPattern.LED_RED);
		// move a bit further -> in front of box
		movement.moveByDistance(20);
		movement.turnRight90();
		if (Button.ESCAPE.isDown()) return ExitCode.USER_INTERRUPT;
		movement.moveByDistance(-12);

		ind.setLED(LEDPattern.LED_GREEN_FLASH);
		// Move forward until Motors stop or push sensor -> Push box against wall
		status = movement.forwardUntil(new OrCondition(new TimeoutCondition(4000),
				                                       new LinesPassedCondition(2)));
		if (status == 99) return ExitCode.USER_INTERRUPT;

		movement.moveByDistance(15);

		ind.setLED(LEDPattern.LED_ORANGE_FLASH);
		// Move around box by one side
		movement.moveByDistance(-4);
		movement.turnRight90();
		if (Button.ESCAPE.isDown()) return ExitCode.USER_INTERRUPT;
		movement.moveByDistance(20);
		movement.turnLeft90();
		status = movement.forwardUntil(new BumperCondition());
		if (status == 99) return ExitCode.USER_INTERRUPT;
		movement.moveByDistance(6);
		movement.moveByDistance(-4);
		movement.turnLeft90();

		// Move box in corner
		status = movement.forwardUntil(new OrCondition(new TimeoutCondition(4000),
				                                       new LinesPassedCondition(1)));
		if (status == 99) return ExitCode.USER_INTERRUPT;

		ind.setLED(LEDPattern.LED_RED_FLASH);
		// Drive to exit
		movement.moveByDistance(-10);
		movement.turnLeft90();
		if (Button.ESCAPE.isDown()) return ExitCode.USER_INTERRUPT;
		movement.moveByDistance(-11);
		movement.moveByDistance(5);
		movement.turnLeft90();


		status = movement.forwardUntil(new BumperCondition());
		if (status == 99) return ExitCode.USER_INTERRUPT;
		movement.moveByDistance(2);
		movement.moveByDistance(-4);

		movement.turnLeft90();
		if (Button.ESCAPE.isDown()) return ExitCode.USER_INTERRUPT;
		movement.moveByDistance(10);
		movement.backward();
		status = movement.waitUntil(new UltrasonicCondition(ComparisonMethod.GREATER, 0.15f));
		if (status == 99) return ExitCode.USER_INTERRUPT;

		movement.stopCorrected();
		movement.turnRight90();
		ind.setLED(LEDPattern.LED_GREEN);
		status = movement.forwardUntil(new OrCondition(new BlueStripCondition(),
				                                       new TimeoutCondition(1000)));
		if (status == 99) return ExitCode.USER_INTERRUPT;

		audio.systemSound(1);
		return ExitCode.SUCCESSFULL;
	}
}
