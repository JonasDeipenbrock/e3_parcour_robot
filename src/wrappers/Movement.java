package wrappers;

import drivingConditions.IDrivingCondition;
import e3base.Configuration;
import lejos.hardware.Button;
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
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		leftMotor.synchronizeWith(new EV3LargeRegulatedMotor[] {rightMotor});
		dPilot = new DifferentialPilot(Configuration.wheelDiameter,
				Configuration.trackWidth, leftMotor, rightMotor, true);
		setAcceleration(500);
		setToMaxSpeed();
	}

	public static Movement getInstance() {
		if (singleton == null) {
			singleton = new Movement();
		}

		return singleton;
	}
	
	public void setSpeed(float speed) {
		leftMotor.startSynchronization();
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.endSynchronization();
	}

	public void setAcceleration(int acceleration) {
		leftMotor.startSynchronization();
		leftMotor.setAcceleration(acceleration);
		rightMotor.setAcceleration(acceleration);
		leftMotor.endSynchronization();
	}
	
	public void setToMaxAcc() {
		setAcceleration(6000);
	}

	public void setToMaxSpeed() {
		setSpeed(leftMotor.getMaxSpeed());
	}
	
	public void moveByDistance(double distance) {
		dPilot.travel(distance);
	}

	/**
	 * Set the motor turn speed with offset
	 * default turn speed is -20
	 * @param offSet
	 */
	public void setMotorRotation(float offSet, float baseSpeed) {
		//float baseSpeed = 150f;
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

	public void forwardUntil(IDrivingCondition condition) {
		float scaleFactor = 5f;
		//get tacho count
		int[] tacho = getTachoCount();
		int startSpeed = leftMotor.getSpeed();
		leftMotor.setSpeed(startSpeed );
		
		rightMotor.setSpeed(startSpeed);
		forward();
		boolean conditionMet = false;
		while(!conditionMet && Button.ENTER.isUp()) {
			try {
				conditionMet = condition.call() != 0;
			} catch (Exception e) {
				System.out.println("Error occured");
				e.printStackTrace();
				conditionMet = true;
			}
			//steer based on tacho count
			int[] currentTacho = getTachoCount();
			int difference = (tacho[0] - currentTacho[0]) - (tacho[1] - currentTacho[1]);
			leftMotor.startSynchronization();
			leftMotor.setSpeed(startSpeed - difference*scaleFactor);
			rightMotor.setSpeed(startSpeed + difference*scaleFactor);
			leftMotor.endSynchronization();
			forward();
		}
		stopCorrected();
		leftMotor.setSpeed(startSpeed);
		rightMotor.setSpeed(startSpeed);
	}

	public int waitUntil(IDrivingCondition condition) {
		int statusCode = 0;
		while(statusCode == 0 && Button.ENTER.isUp()) {
			try {
				statusCode = condition.call();
			} catch (Exception e) {
				System.out.println("Error occured");
				e.printStackTrace();
				statusCode = 1;
			}
		}
		return statusCode;
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
	
	public void stopCorrected() {
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

	public void stop() {
		leftMotor.startSynchronization();
		leftMotor.stop(false);
		rightMotor.stop(false);
		leftMotor.endSynchronization();
	}
	
	public void turnLeft() {
		rightMotor.backward();
		leftMotor.forward();
	}
	
	public void turnRight() {
		leftMotor.backward();
		rightMotor.forward();
	}

	public void turn(double angle) {
		dPilot.rotate(angle);
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
