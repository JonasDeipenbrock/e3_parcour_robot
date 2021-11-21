package levelSolver;

import e3base.TachoTimeout;
import lejos.hardware.Button;
import lejos.utility.Delay;
import wrappers.BumperSensor;
import wrappers.ColorSensor;
import wrappers.Movement;

public class LineFollowing implements ILevelSolver {

	float k = 4000;
	Movement move;
	ColorSensor sensor;
	BumperSensor bumper;
	TachoTimeout tTimeout;
	
	
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
			follow();
			if(tTimeout.updateCounter() > 200) {
				System.out.println("lost the line");
				refind();
				tTimeout.resetCounter(); //reset timeout
			}
			Delay.msDelay(100);
			System.out.println(tTimeout.updateCounter());
		}
		System.out.println("Bumped into something");
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
		if(sensor.checkBlue()) return false;
		return true;
	}
	
	/**
	 * Follows a line
	 */
	void follow() {
		float value = sensor.getGreyScale();
		float error = (sensor.getBorderValue() - value) * k;
		if(value > 0.08) {
			tTimeout.resetCounter();
		}
		move.setMotorRotation(error);
	}
	
	/**
	 * Refinds the line
	 */
	void refind() {
		Delay.msDelay(10000);
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
