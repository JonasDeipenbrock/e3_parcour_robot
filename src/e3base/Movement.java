package e3base;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.utility.Delay;

public class Movement {

	static private Movement singleton;
    private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;

    private Movement() {
		leftMotor = new EV3LargeRegulatedMotor(Configuration.leftMotorPort);
		rightMotor = new EV3LargeRegulatedMotor(Configuration.rightMotorPort);
		leftMotor.setSpeed(leftMotor.getMaxSpeed());
		rightMotor.setSpeed(rightMotor.getMaxSpeed());
		EV3LargeRegulatedMotor[] motorArray = {rightMotor};
		leftMotor.synchronizeWith(motorArray);
    }
    
    public static Movement getInstance() {
        if(singleton == null) {
            singleton = new Movement();
        }
        
        return singleton;
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
	
	public void turnLeft90() {
		left();
		Delay.msDelay(750);
		stop();
	}
	
	public void turnRight90() {
		right();
		Delay.msDelay(750);
		stop();
	}
}
