package levelSolver;

import drivingConditions.*;
import lejos.hardware.Button;
import wrappers.ExitCode;
import wrappers.Movement;
import wrappers.UltrasonicSensor;

public class BridgeCrossing implements ILevelSolver {
	
	private Movement movement;
	private UltrasonicSensor usSensor;
	
	public BridgeCrossing() {
		movement = Movement.getInstance();
		usSensor = UltrasonicSensor.getInstance();
	}

	@Override
	public ExitCode run() {
		System.out.println("Starting bridge crossing");

		IDrivingCondition forwardCond = new OrCondition(
				new UltrasonicCondition(ComparisonMethod.GREATER, 0.07f),
				new BlueStripCondition(),
				new BumperCondition(),
				new ButtonCondition());

		IDrivingCondition turnCond = new OrCondition(
				new UltrasonicCondition(ComparisonMethod.LESS, 0.06f),
				new BlueStripCondition(),
				new BumperCondition(),
				new ButtonCondition());

		movement.moveByDistance(15);
		while (true) {
			//drive forward in slight arc
			movement.setMotorRotation(50, 300);
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
			movement.moveByDistance(-5);
			movement.setSpeed(400f);
			movement.turnLeft();
			status = movement.waitUntil(turnCond);
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

			movement.turn(30);
		}
	}

	private void handleStatus() {

	}

	private void bumperNavigate() {
		//TODO: find entrance code
	}
}
