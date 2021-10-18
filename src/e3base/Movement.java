package e3base;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Movement {
	
	EV3LargeRegulatedMotor leftMotor;
	EV3LargeRegulatedMotor rightMotor;
	
	public Movement(EV3LargeRegulatedMotor left, EV3LargeRegulatedMotor right) {
		leftMotor = left;
		rightMotor = right;
		leftMotor.setSpeed(leftMotor.getMaxSpeed());
		rightMotor.setSpeed(rightMotor.getMaxSpeed());
	}

	public void left() {
		leftMotor.backward();
		rightMotor.forward();
	}
	public void right() {
		leftMotor.forward();
		rightMotor.backward();
	}
	
	public void forward() {
		leftMotor.forward();
		rightMotor.forward();
	}
	public void backwards() {
		leftMotor.backward();
		rightMotor.backward();
	}
	public void stop() {
		leftMotor.stop(true);
		rightMotor.stop(true);
	}
}
