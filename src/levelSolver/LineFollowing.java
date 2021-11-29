package levelSolver;

import e3base.Base;
import e3base.Helper;
import e3base.TachoTimeout;
import lejos.utility.Delay;
import wrappers.BumperSensor;
import wrappers.ColorSensor;
import wrappers.Movement;

public class LineFollowing implements ILevelSolver {

	final float K = 4000;
	Movement move;
	ColorSensor sensor;
	BumperSensor bumper;
	TachoTimeout tTimeout;
	Helper helper;
	
	boolean onLineFlag = true;
	
	public LineFollowing() {
		move = Movement.getInstance();
		sensor = ColorSensor.getInstance();
		bumper = BumperSensor.getInstance();
		helper = Helper.getInstance();
		tTimeout = new TachoTimeout();
	}
	
	
	@Override
	public void run() {
		int iteration = 1;
		int generations = 50;
		float[] buffer = new float[generations];
		
		Delay.msDelay(1000);
		move.forward();
		while(helper.checkLoop(true)) {
			float currentError = calculateError();
			int rest = iteration % generations;
			buffer[rest] = currentError;
			if(iteration % generations == 0) {			
				float errorSum = 0;
				for (float err: buffer) {
					errorSum += err;
				}
				float medianError = errorSum / generations;
				System.out.println(medianError);
				follow(medianError);
			}
			iteration++;
//			if(onLineFlag) {
//				//continue following the line
//				follow();
//				
//			} else {
//				//search for line
//				if(refind()) {
//					onLineFlag = true;
//				}
//			}
			
			//delay to limit fetching of samples and make movement smoother
			// current value is 1/20 of a second as polling rate / delay
			//Delay.msDelay(50);
		}
		System.out.println("Line following ended");
		move.stop();
	}
	
	float calculateError() {
		//get grey value and calculate error
		float value = sensor.getGreyScale();
		if(value > 0.08) {
			tTimeout.resetCounter();
		}
		
		float error = (sensor.getBorderValue() - value) * K;
		return error;
	}
	
	/**
	 * Follows a line
	 */
	void follow(float error) {
		//reset timeout if still on line
		//set rotation from error
		move.setMotorRotation(error);
		
		//check if the line is missing for to long
		if(tTimeout.updateCounter() > 400) {
			System.out.println("lost the line");
			onLineFlag = false;
			tTimeout.resetCounter(); //reset timeout
		}
	}
	
	
	/**
	 * Refinds the line
	 * @return true if the line has been refound, false otherwise
	 * 
	 * Currently this function only searches 720 motor degree towards one side
	 * Also this method is blocking due to the inner while loop and cant break on collision
	 */
	boolean refind() {
		move.stop();
		final int[] start_tachos = move.getTachoCount();
		//move.turnLeft90();
		move.turnLeft();
		int degree = 360;
		
		while(helper.checkLoop(true)) {
			if(searchLineTask()) {
				return true;
			}
			if(turnRobotTask(start_tachos[1], degree)) {
				move.stop();
				Base.endProgram();
				System.exit(1);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * turns the robot
	 * @return
	 */
	boolean turnRobotTask(int start_tacho, int degree) {
		int count = move.getTachoCount()[1];
		System.out.println(String.format("%d, %d", count, start_tacho - degree));
		if(count > start_tacho + degree) {
			return true;
		}
		return false;
	}
	
	/**
	 * checks if on the line
	 * @return true if the line has been found
	 */
	boolean searchLineTask() {
		if(sensor.getGreyScale() > 0.08f) {
			return true;
		}
		return false;
	}
	
	/**
	 * Drives around a fixes object in its path
	 */
	void bypass() {
		
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
