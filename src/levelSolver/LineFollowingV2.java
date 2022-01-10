package levelSolver;

import lejos.hardware.Button;
import wrappers.BumperSensor;
import wrappers.ColorSensor;
import wrappers.ExitCode;
import wrappers.Movement;

public class LineFollowingV2 implements ILevelSolver {

	final float ERROR_FACTOR = 4000;
	Movement move;
	BumperSensor bumper;
	ColorSensor sensor;
	
	//total loss
	float totalLoss = 0f;
	
	//loss buffer values
	int iteration = 1;
	final int GENERATIONS = 50;
	float[] buffer = new float[GENERATIONS];
	
	@Override
	public ExitCode run() {
		move = Movement.getInstance();
		bumper = BumperSensor.getInstance();
		sensor = ColorSensor.getInstance();
		
		//initialize fields
		
		
		/**
		 * Exit codes:
		 * 0 = Successfully completed until blue line
		 * 1 = Lossed Line => refind line and restart following
		 * 2 = Bumped into box => start box avoidance and restart following
		 * 3 = User interrupt, end program
		 */
		
		//start driving forward
		while(true) {
			ExitCode exitCode = followLine();
			switch (exitCode) {
				case SUCCESSFULL:
					System.out.println("Finished line following");
					move.stop();
					return ExitCode.SUCCESSFULL;
				case LINE_LOSS_INTERRUPT:
					refindLine();
					break;
				case BUMPER_INTERRUPT:
					avoidBox();
					break;
				case USER_INTERRUPT:
					System.out.println("User interrupt");
					move.stop();
					return ExitCode.USER_INTERRUPT;
			}
			return ExitCode.SUCCESSFULL;
		}
		
		
		//if total loss to high jump to refind line

	}
	
	ExitCode followLine() {
		float error = 0f;
		//! Error not in while but total error!
		while(true) {
			//if bumped => return 2
			if(bumper.anyBumbed()) return ExitCode.BUMPER_INTERRUPT;
			
			//if blue line => return 0
			//TODO add blue line
			//if(sensor.checkBlue()) return ExitCode.SUCCESSFULL;
			
			//if button pressed => return 1
			if(Button.ESCAPE.isDown()) return ExitCode.USER_INTERRUPT;
			
			//if total loss too big => return 3
			
			//measure loss and adjust for it
			if(calculateError()) {
				error = getBufferedError();
				move.setMotorRotation(error, 150f);
				System.out.println(error);
			}
			
			//summ total loss
			
			//reset total loss if still on line

			move.forward();
		}
		
	}
	

	void refindLine() {
		System.out.println("Refinding line");
	}
	
	void avoidBox() {
		System.out.println("Avoiding box");
		move.moveByDistance(-7);
		move.turnLeft90();
		move.moveByDistance(17);
		move.turnRight90();
		move.moveByDistance(38);
		move.turn(-65);
		move.moveByDistance(20);
	}
	
	/**
	 * Calculates the error and check if on new generation
	 * @return
	 */
	private boolean calculateError() {
		//get grey value and calculate error
		float value = sensor.getGreyScale();
		float error = (sensor.getBorderValue() - value) * ERROR_FACTOR;
		//System.out.println(error);
		//fill buffer
		int rest = iteration % GENERATIONS;
		buffer[rest] = error;
		
		if(value > 0.08) {
			//TODO reset total error
			
			//tTimeout.resetCounter();
		}
		iteration++;
		if(rest == 0) return true;
		return false;
	}
	
	private float getBufferedError() {
		float errorSum = 0;
		for (float err: buffer) {
			errorSum += err;
		}
		float medianError = errorSum / GENERATIONS;
		return medianError;
	}
}
