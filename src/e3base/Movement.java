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
		EV3LargeRegulatedMotor[] motorArray = {rightMotor};
		leftMotor.synchronizeWith(motorArray);
	}

	public void left() {
		leftMotor.startSynchronization();
		leftMotor.backward();
		rightMotor.forward();
		leftMotor.endSynchronization();
	}
	public void right() {
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.backward();
		leftMotor.endSynchronization();
	}
	
	public void forward() {
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();
	}
	public void backwards() {
		leftMotor.startSynchronization();
		leftMotor.backward();
		rightMotor.backward();
		leftMotor.endSynchronization();
	}
	public void stop() {
		leftMotor.stop(true);
		rightMotor.stop(true);
	}
	
	public void turnLeft90() throws InterruptedException {
		left();
		Thread.sleep(750);
		stop();
	}
	
	public void turnRight90() throws InterruptedException {
		right();
		Thread.sleep(750);
		stop();
	}
}
