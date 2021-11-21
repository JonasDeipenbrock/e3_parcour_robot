package levelSolver;

import e3base.Base;
import e3base.TachoTimeout;
import lejos.hardware.Button;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;
import wrappers.ColorSensor;
import wrappers.Movement;

public class LineFollowing implements ILevelSolver {

	float k = 4000;
	Movement move;
	ColorSensor sensor;
	EV3TouchSensor bumperLeft;
	EV3TouchSensor bumperRight;
	TachoTimeout tTimeout;
	
	
	public LineFollowing() {
		move = Movement.getInstance();
		sensor = ColorSensor.getInstance();
		bumperLeft = Base.leftTouch;
		bumperRight = Base.rightTouch;
		tTimeout = new TachoTimeout();
	}
	
	@Override
	public void run() {
		float[] bumperLeftValue = {0f};
		float[] bumperRightValue = {0f};
		Delay.msDelay(1000);
		move.forward();
		while(checkLoop(bumperLeftValue[0], bumperRightValue[0])) {
			follow();
			//fetch new samples
			bumperLeft.fetchSample(bumperLeftValue, 0);
			bumperRight.fetchSample(bumperRightValue, 0);
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
	
	public boolean checkLoop(float bumperLeftValue, float bumperRightValue) {
		return (bumperLeftValue == 0 && bumperRightValue == 0) && Button.ENTER.isUp();
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
