package wrappers;

import e3base.Configuration;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

public class Movement {

	static private Movement singleton;
	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;
	private DifferentialPilot dPilot;

	private Movement() {
		leftMotor = new EV3LargeRegulatedMotor(Configuration.leftMotorPort);
		rightMotor = new EV3LargeRegulatedMotor(Configuration.rightMotorPort);
		EV3LargeRegulatedMotor[] rightMotorArray = {rightMotor};
		leftMotor.synchronizeWith(rightMotorArray);
		dPilot = new DifferentialPilot(Configuration.wheelDiameter,
				Configuration.trackWidth, leftMotor, rightMotor, true);
		//leftMotor.setSpeed();
		//rightMotor.setSpeed(-200);
		//dPilot.setTravelSpeed(dPilot.getMaxTravelSpeed());
		
	}

	public static Movement getInstance() {
		if (singleton == null) {
			singleton = new Movement();
		}

		return singleton;
	}
	
	public void setSpeed(float speed) {
		final float offset = 0.75f;
		leftMotor.startSynchronization();
		leftMotor.setSpeed(speed * offset);
		rightMotor.setSpeed(speed);
		leftMotor.endSynchronization();
	}

	public void setToMaxSpeed() {
		setSpeed(leftMotor.getMaxSpeed());
	}
	
	public void moveByDistance(double distance) {
		dPilot.travel(distance);
	}
	
	public void steer(double turnRate) {
//		dPilot.steer();
		dPilot.steer(turnRate, 30, true);
	}
	 
	/**
	 * Set the motor turn speed with offset
	 * default turn speed is -20
	 * @param offSet
	 */
	public void setMotorRotation(float offSet) {
		float baseSpeed = 150f;
		float leftSpeed = baseSpeed + offSet;
		float rightSpeed = baseSpeed - offSet;
		
		if(leftSpeed < 0) {
			leftSpeed = Math.abs(leftSpeed);
			leftMotor.startSynchronization();
			leftMotor.setSpeed(leftSpeed);
			rightMotor.setSpeed(rightSpeed);
			leftMotor.forward();
			rightMotor.backward();
			leftMotor.endSynchronization();
		} else if(rightSpeed < 0) {
			rightSpeed = Math.abs(rightSpeed);
			leftMotor.startSynchronization();
			rightMotor.setSpeed(rightSpeed);
			leftMotor.setSpeed(leftSpeed);
			rightMotor.forward();
			leftMotor.backward();
			leftMotor.endSynchronization();
		} else {
			leftMotor.startSynchronization();
			rightMotor.setSpeed(rightSpeed);
			leftMotor.setSpeed(leftSpeed);
			rightMotor.backward();
			leftMotor.backward();
			leftMotor.endSynchronization();
		}
	}
	
	public void forward() {
		leftMotor.startSynchronization();
		leftMotor.backward();
		rightMotor.backward();
		leftMotor.endSynchronization();
	}
	
	public void backward() {
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();
	}
	
	public void stop() {
		leftMotor.startSynchronization();
		leftMotor.stop();
		rightMotor.stop();
		leftMotor.endSynchronization();
	}
	
	public void turnLeft() {
		rightMotor.backward();
	}
	
	public void turnRight() {
		leftMotor.backward();
	}

	public void turn(double angle) {
		dPilot.rotate(angle);
	}

	public void forwardPilot() {
		dPilot.forward();
	}

	public void backwardsPilot() {
		dPilot.backward();
	}

	public void stopPilot() {
		dPilot.stop();
	}

	public void turnLeft90() {
		turn(90);
	}

	public void turnRight90() {
		turn(-90);
	}
	
	public int[] getTachoCount() {
		int left = leftMotor.getTachoCount();
		int right = rightMotor.getTachoCount();
		int[] count = {left, right};
		return count;
	}

	public boolean motorStalles() {
		return leftMotor.isStalled() || rightMotor.isStalled();
	}
	
	public static void close() {
		if (singleton != null) {
			singleton.leftMotor.close();
			singleton.rightMotor.close();
			singleton = null;
		}
	}
}
