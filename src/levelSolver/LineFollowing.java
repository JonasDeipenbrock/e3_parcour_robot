package levelSolver;

import e3base.Base;
import e3base.Movement;
import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;

public class LineFollowing implements ILevelSolver {

	float blackValue = 0.01f;
	float whiteValue = 0.15f;
	float ideal = 0.10f;
	Movement move;
	EV3ColorSensor sensor;
	EV3TouchSensor bumperLeft;
	EV3TouchSensor bumperRight;
	
	public LineFollowing() {
		move = Movement.getInstance();
		sensor = Base.colorSensor;
		bumperLeft = Base.leftTouch;
		bumperRight = Base.rightTouch;
		sensor.setCurrentMode(1);
	}
	
	@Override
	public void run() {
		float[] bumperLeftValue = {0f};
		float[] bumperRightValue = {0f};
		move.forward();
		while((bumperLeftValue[0] == 0 && bumperRightValue[0] == 0) && Button.ENTER.isUp()) {
			follow();
			
			//fetch new samples
			bumperLeft.fetchSample(bumperLeftValue, 0);
			bumperRight.fetchSample(bumperRightValue, 0);			
		}
		System.out.println("Bumped into something");
		move.stop();

	}
	
	/**
	 * Follows a line
	 */
	void follow() {
		float[] value = {0.15f};
		sensor.fetchSample(value, 0);
		float error = ideal - value[0];
		
	}
	
	/**
	 * Refinds the line
	 */
	void refind() {
		
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
