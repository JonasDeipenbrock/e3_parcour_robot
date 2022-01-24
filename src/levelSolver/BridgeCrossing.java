package levelSolver;

import drivingConditions.*;
import lejos.hardware.Audio;
import lejos.hardware.ev3.LocalEV3;
import wrappers.ExitCode;
import wrappers.Movement;
import wrappers.UltrasonicPosition;
import wrappers.UltrasonicSensor;

public class BridgeCrossing implements ILevelSolver {
	
	private Movement movement;
	private UltrasonicSensor usSensor;
	private UltrasonicPosition uPosition;
	Audio audio;
	
	public BridgeCrossing() {
		movement = Movement.getInstance();
		usSensor = UltrasonicSensor.getInstance();
		uPosition = UltrasonicPosition.getInstance();
		audio = LocalEV3.get().getAudio();
	}

	@Override
	public ExitCode run() {
		System.out.println("Starting bridge crossing");
		movement.setToMaxAcc();
		movement.setToMaxSpeed();
		uPosition.moveDown();

		IDrivingCondition forwardCond = new OrCondition(
				new UltrasonicCondition(ComparisonMethod.GREATER, 0.07f),
				new BlueStripCondition(10),
				new BumperCondition(),
				new ButtonCondition());

		IDrivingCondition turnCond = new OrCondition(
				new UltrasonicCondition(ComparisonMethod.LESS, 0.06f),
				new BlueStripCondition(10),
				new BumperCondition(),
				new ButtonCondition());

		movement.moveByDistance(25);
		while (true) {
			//drive forward in slight arc
			movement.setMotorRotation(60f, 400f);
			movement.forward();
			int status = movement.waitUntil(forwardCond);
			movement.stop();

			if (status == 2) {
				System.out.println("Detected Blue Strip");
				return ExitCode.SUCCESSFULL;
			} else if (status == 3) {
				System.out.println("Bumper pushed. Starting correction code");
				bumperNavigate();
				return ExitCode.SUCCESSFULL;
			} else if (status == 4) {
				System.out.println("Button pushed. Exiting");
				return ExitCode.USER_INTERRUPT;
			}


			if (usSensor.getDistance() >= 1000) {
				System.out.println("Infinity");
				//return ExitCode.SUCCESSFULL;
			}

			//drive back and turn left
			movement.setSpeed(400f);
			movement.moveByDistance(-3); //original -5
			movement.turnLeft();
			status = movement.waitUntil(turnCond);
			movement.stop();
			if (status == 2) {
				System.out.println("Detected Blue Strip");
				audio.systemSound(1);
				return ExitCode.SUCCESSFULL;
			} else if (status == 3) {
				System.out.println("Bumper pushed. Starting correction code");
				audio.systemSound(1);
				bumperNavigate();
				return ExitCode.SUCCESSFULL;
			} else if (status == 4) {
				System.out.println("Button pushed. Exiting");
				return ExitCode.USER_INTERRUPT;
			}

			movement.turn(30); //original 30
		}
	}

	private void bumperNavigate() {
		movement.moveByDistance(-5);
		movement.turn(30);
		movement.forward();
		int status = movement.waitUntil(new OrCondition(new BlueStripCondition(10),
				                           new ButtonCondition(),
				                           new TimeoutCondition(1000),
				                           new BumperCondition()));
		movement.stop();
		// second time bumper hit
		if (status == 4) {
			movement.moveByDistance(-5);
			movement.turn(-25);
			movement.moveByDistance(6);
		}
	}
}
