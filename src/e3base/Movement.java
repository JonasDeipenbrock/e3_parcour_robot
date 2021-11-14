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
		dPilot = new DifferentialPilot(Configuration.wheelDiameter,
				Configuration.trackWidth, leftMotor, rightMotor, true);
		
		dPilot.setTravelSpeed(dPilot.getMaxTravelSpeed());
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
	
	public void steer() {
//		dPilot.steer();
	}

	public void turn(double angle) {
		dPilot.rotate(angle);
	}

	public void forward() {
		dPilot.forward();
	}

	public void backwards() {
		dPilot.backward();
	}

	public void stop() {
		dPilot.stop();
	}

	public void turnLeft90() {
		turn(-90);
	}

	public void turnRight90() {
		turn(90);
	}
	
	public static void close() {
		if (singleton != null) {
			singleton.leftMotor.close();
			singleton.rightMotor.close();
		}
	}
}
