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
		while(bumperLeftValue[0] != 1 || bumperRightValue[0] != 1) {
			follow();
			
			//fetch new samples
			bumperLeft.fetchSample(bumperLeftValue, 0);
			bumperRight.fetchSample(bumperRightValue, 0);			
		}
		System.out.println("Bumped into something");

	}
	
	/**
	 * Follows a line
	 */
	void follow() {
		float[] value = {0.15f};
		float error = 0;
		while(Button.ENTER.isUp()) {
			sensor.fetchSample(value, 0);
			error = ideal - value[0];
		}
		System.out.println(error);
		System.out.println(sensor.getAvailableModes());
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
