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
		//leftMotor.synchronizeWith(rightMotor);
		dPilot = new DifferentialPilot(Configuration.wheelDiameter,
				Configuration.trackWidth, leftMotor, rightMotor, true);
		leftMotor.setSpeed(-200);
		rightMotor.setSpeed(-200);
		//dPilot.setTravelSpeed(dPilot.getMaxTravelSpeed());
	}

	public static Movement getInstance() {
		if (singleton == null) {
			singleton = new Movement();
		}

		return singleton;
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
			leftMotor.setSpeed(leftSpeed);
			rightMotor.setSpeed(rightSpeed);
			leftMotor.forward();
			rightMotor.backward();
		} else if(rightSpeed < 0) {
			rightSpeed = Math.abs(rightSpeed);
			rightMotor.setSpeed(rightSpeed);
			leftMotor.setSpeed(leftSpeed);
			rightMotor.forward();
			leftMotor.backward();
		} else {
			rightMotor.setSpeed(rightSpeed);
			leftMotor.setSpeed(leftSpeed);
			rightMotor.backward();
			leftMotor.backward();
		}
	}
	
	public void forward() {
		leftMotor.backward();
		rightMotor.backward();
	}
	
	public void stop() {
		leftMotor.stop();
		rightMotor.stop();
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
	
	public static void close() {
		if (singleton != null) {
			singleton.leftMotor.close();
			singleton.rightMotor.close();
			singleton = null;
		}
	}
}
