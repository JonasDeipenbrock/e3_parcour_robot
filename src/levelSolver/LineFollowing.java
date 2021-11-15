package levelSolver;

import e3base.Base;
import e3base.ColorSensor;
import e3base.Movement;
import lejos.hardware.Button;
import lejos.hardware.sensor.EV3TouchSensor;

public class LineFollowing implements ILevelSolver {

	float blackValue = 0.01f;
	float whiteValue = 0.41f;
	float ideal = 0.20f;
	float k = 300;
	Movement move;
	ColorSensor sensor;
	EV3TouchSensor bumperLeft;
	EV3TouchSensor bumperRight;
	int timeOut = 0;
	
	
	public LineFollowing() {
		move = Movement.getInstance();
		sensor = ColorSensor.getInstance();
		bumperLeft = Base.leftTouch;
		bumperRight = Base.rightTouch;
	}
	
	@Override
	public void run() {
		float[] bumperLeftValue = {0f};
		float[] bumperRightValue = {0f};
		//move.setMotorRotation(0);
		//Delay.msDelay(10000);
		move.forward();
		while(checkLoop(bumperLeftValue[0], bumperRightValue[0])) {
			follow();
			//fetch new samples
			bumperLeft.fetchSample(bumperLeftValue, 0);
			bumperRight.fetchSample(bumperRightValue, 0);			
		}
		System.out.println("Bumped into something");
		move.stop();

	}
	
	public boolean checkLoop(float bumperLeftValue, float bumperRightValue) {
		if(timeOut > 10) {
			System.out.println(timeOut);
		}
		return (bumperLeftValue == 0 && bumperRightValue == 0) && Button.ENTER.isUp();
	}
	
	/**
	 * Follows a line
	 */
	void follow() {
		float[] value = sensor.getColorData();
		float error = ideal - value[0];
//		if(value[0] < 0.10F) {
//			System.out.println("black");
//			timeOut++;
//		}
		System.out.println(k * error);
		//move.steer(k * error);
		move.setMotorRotation(k * error);
		move.forward();
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
