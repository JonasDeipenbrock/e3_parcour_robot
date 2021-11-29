package wrappers;

import e3base.Configuration;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class Movement {

	static private Movement singleton;
	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;
	private DifferentialPilot dPilot;
	

	private Movement() {
		leftMotor = new EV3LargeRegulatedMotor(Configuration.leftMotorPort);
		rightMotor = new EV3LargeRegulatedMotor(Configuration.rightMotorPort);
		dPilot = new DifferentialPilot(Configuration.wheelDiameter,
				Configuration.trackWidth, leftMotor, rightMotor, true);
		leftMotor.setAcceleration(500);
		rightMotor.setAcceleration(500);
		setSpeed(leftMotor.getMaxSpeed(), 1);
	}

	public static Movement getInstance() {
		if (singleton == null) {
			singleton = new Movement();
		}

		return singleton;
	}
	
	public void setSpeed(float speed, float offset) {
		//final float offset = 0.75f;
		leftMotor.startSynchronization();
		leftMotor.setSpeed(speed * offset);
		rightMotor.setSpeed(speed);
		leftMotor.endSynchronization();
	}

	public void setToMaxSpeed() {
		setSpeed(leftMotor.getMaxSpeed(), 1);
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
		leftMotor.synchronizeWith(new EV3LargeRegulatedMotor[] {rightMotor});
		leftMotor.startSynchronization();
		leftMotor.backward();
//		leftMotor.rotate(-1000, true);
		rightMotor.backward();
//		rightMotor.rotate(-1000,true);
		leftMotor.endSynchronization();
	}
	
	public void backward() {
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();
	}
	
	public void stop() {
		int[] startTacho = getTachoCount();
		leftMotor.startSynchronization();
		leftMotor.stop(false);
		rightMotor.stop(false);
		leftMotor.endSynchronization();
		while((leftMotor.isMoving() || rightMotor.isMoving()) && Button.ENTER.isUp()) {
			
		}
		int[] endTacho = getTachoCount();
		int difference = (endTacho[0] - startTacho[0]) - (endTacho[1] - startTacho[1]) ;
		difference *= 1.1;
		if (difference > 0) {
			rightMotor.rotate(difference);
		}else if (difference < 0) {
			leftMotor.rotate(-difference);
		}
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
