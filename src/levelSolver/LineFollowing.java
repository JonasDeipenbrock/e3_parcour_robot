package levelSolver;

import e3base.TachoTimeout;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
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
	
	boolean onLineFlag = true;
	
	
	public LineFollowing() {
		move = Movement.getInstance();
		sensor = ColorSensor.getInstance();
		bumper = BumperSensor.getInstance();
		tTimeout = new TachoTimeout();
	}
	
	@Override
	public void run() {
		Delay.msDelay(1000);
		move.forward();
		while(checkLoop()) {
			if(onLineFlag) {
				//continue following the line
				follow();
				
			} else {
				//search for line
				if(refind()) {
					onLineFlag = true;
				}
			}
			
			//delay to limit fetching of samples and make movement smoother
			// current value is 1/10 of a second as polling rate / delay
			Delay.msDelay(100);
		}
		System.out.println("Line following ended");
		move.stop();

	}
	
	/**
	 * Check if break condition is already met
	 * @return
	 */
	public boolean checkLoop() {
		if(Button.ENTER.isDown()) return false;
		//this should not be checked here cause we cant drive around the box this way
		if(bumper.anyBumbed()) return false;	
		if(sensor.checkBlue()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Follows a line
	 */
	void follow() {
		//get grey value and calculate error
		float value = sensor.getGreyScale();
		float error = (sensor.getBorderValue() - value) * K;
		//reset timeout if still on line
		if(value > 0.08) {
			tTimeout.resetCounter();
		}
		//set rotation from error
		move.setMotorRotation(error);
		
		//check if the line is missing for to long
		if(tTimeout.updateCounter() > 200) {
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
		final int start_tacho = Motor.A.getTachoCount();
		Motor.A.forward();
		int degree = 720;
		
		while(true) {
			if(searchLineTask()) {
				return true;
			}
			if(turnRobotTask(start_tacho, degree)) {
				return false;
			}
		}
	}
	
	/**
	 * turns the robot
	 * @return
	 */
	boolean turnRobotTask(int start_tacho, int degree) {
		if(Motor.A.getTachoCount() > start_tacho + degree) {
			return true;
		}
		return false;
	}
	
	/**
	 * checks if on the line
	 * @return true if the line has been found
	 */
	boolean searchLineTask() {
		if(sensor.getGreyScale() > 0.5) {
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
