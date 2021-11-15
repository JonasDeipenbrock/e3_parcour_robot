package e3base;

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
		float baseSpeed = -150f;
		//System.out.println(baseSpeed - offSet);
		leftMotor.setSpeed(baseSpeed + offSet);
		rightMotor.setSpeed(baseSpeed - offSet);
		//System.out.println(leftMotor.getRotationSpeed());
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
	
	public static void close() {
		if (singleton != null) {
			singleton.leftMotor.close();
			singleton.rightMotor.close();
			singleton = null;
		}
	}
}
