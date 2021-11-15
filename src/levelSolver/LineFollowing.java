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
		init();
		
		
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
	
	public void init() {
		System.out.println("Set on black");
		Button.waitForAnyPress();
		float[] dataBlack = sensor.getColorData();
		System.out.println(String.format("black: %f, %f, %f", dataBlack[0], dataBlack[1], dataBlack[2]));
		System.out.println("Set on line border");
		Button.waitForAnyPress();
		float[] dataBorder = sensor.getColorData();
		System.out.println(String.format("border: %f, %f, %f", dataBorder[0], dataBorder[1], dataBorder[2]));
		System.out.println("Set on white");
		Button.waitForAnyPress();
		float[] dataWhite = sensor.getColorData();
		System.out.println(String.format("white: %f, %f, %f", dataWhite[0], dataWhite[1], dataWhite[2]));
		System.out.println("Press any button to continue");
		Button.waitForAnyPress();
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
		//not correct rn
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
