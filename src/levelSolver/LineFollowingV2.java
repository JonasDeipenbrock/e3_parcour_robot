package levelSolver;

import lejos.hardware.Audio;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;
import wrappers.BumperSensor;
import wrappers.ColorSensor;
import wrappers.ExitCode;
import wrappers.Movement;

public class LineFollowingV2 implements ILevelSolver {

	boolean afterBoxFlag = false;
	
	final float ERROR_FACTOR = 2000;	//4000
	Movement move;
	BumperSensor bumper;
	ColorSensor sensor;
	Audio audio;
	
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
		audio = LocalEV3.get().getAudio();
		
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
					audio.systemSound(1);
					return ExitCode.SUCCESSFULL;
				case LINE_LOSS_INTERRUPT:
					refindLine();
					break;
				case BUMPER_INTERRUPT:
					avoidBox();
					afterBoxFlag = true;
					break;
				case USER_INTERRUPT:
					System.out.println("User interrupt");
					move.stop();
					return ExitCode.USER_INTERRUPT;
			}
		}
		
		
		//if total loss to high jump to refind line

	}
	
	ExitCode followLine() {
		float error = 0f;
		//! Error not in while but total error!
		while(true) {
			Delay.msDelay(100);
			//if bumped => return 2
			if(bumper.anyBumbed()) return ExitCode.BUMPER_INTERRUPT;
			
			//if blue line => return 0
			//TODO add blue line
			//if(sensor.checkBlue()) return ExitCode.SUCCESSFULL;
			
			//if button pressed => return 1
			if(Button.ESCAPE.isDown()) return ExitCode.USER_INTERRUPT;
			
			//if total loss too big => return 3
			if(totalLoss > 100) {
				//System.out.println(String.format("Total error reached %f", totalLoss));
			}
			float newError = scaleToRange(calculateErrorDouble());
			System.out.println(String.format("Error: %3.3f", newError));
			if(newError > 200) {
				//System.out.println(String.format("Sharp corner, %3.3f", newError));
				move.setMotorRotation(150, 0);
			} else if(newError < -200) {
				//System.out.println(String.format("Sharp -corner, %3.3f", newError));
				move.setMotorRotation(-150, 0);
			} else {
				move.setMotorRotationZeroed(newError, 100f);
			}
			//measure loss and adjust for it
			//if(calculateError()) {
				//error = getBufferedError();
				//move.setMotorRotationZeroed(scaleToRange(error), 150f);
				//System.out.println(String.format("%f, %f", error, scaleToRange(error)));
			//}
			
			//summ total loss
			
			//reset total loss if still on line

			//move.forward();
		}
		
	}
	
	float scaleToRange(float x) {
		// original boundary is where the values are beforehand
		float upperOriginalBound = 200;
		float lowerOriginalBound = -200;
		// range is where they should be later on
		float lowerRange = -300;
		float upperRange = 300;
		float top = (upperRange - lowerRange) * (x - lowerOriginalBound);
		float bottom = (upperOriginalBound - lowerOriginalBound);
		return top / bottom + lowerRange;
	}
	
	/**
     * 0,021 0,037 0,014 => schwarz: 0,024
     * 0,137 0,202 0,155 => weiß: 0,164
     * 0,083 0,142 0,075 => 0,1
     * 
     * Errors:
     * 4000 => 2000 => 400
     * 330 => 162 => 31
     * -260 => -112 => -19
     */
	
	void refindLine() {
		System.out.println("Refinding line");
		while(true) {
			//if button pressed => return 1
			if(Button.ESCAPE.isDown()) return;
			float value = sensor.getGreyScale();
			if(value > 0.08) {
				return;
			}
		}
	}
	
	void ninety() {
		System.out.println("Sharp corner");
		move.setMotorRotation(150, 0);
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
	
	private float calculateErrorDouble() {
		float value = sensor.getGreyScale();
		float error = (sensor.getBorderValue() - value) * ERROR_FACTOR;
		
		totalLoss++;
		if(value > 0.08) {
			//TODO reset total error
			totalLoss = 0f;
			//tTimeout.resetCounter();
		}
		
		return error;
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
		
		totalLoss++;
		if(value > 0.08) {
			//TODO reset total error
			totalLoss = 0f;
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
