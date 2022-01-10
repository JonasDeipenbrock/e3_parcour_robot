package levelSolver;

import lejos.hardware.Button;
import wrappers.BumperSensor;
import wrappers.ColorSensor;
import wrappers.Movement;

public class LineFollowingV2 implements ILevelSolver {

	final float ERROR_FACTOR = 4000;
	Movement move;
	BumperSensor bumper;
	ColorSensor sensor;
	
	//total loss
	float totalLoss = 0f;
	
	//loss buffer values
	int iteration = 1;
	final int GENERATIONS = 50;
	float[] buffer = new float[GENERATIONS];
	
	@Override
	public void run() {
		move = Movement.getInstance();
		bumper = BumperSensor.getInstance();
		sensor = ColorSensor.getInstance();
		
		//initialize fields
		
		
		/**
		 * Exit codes:
		 * 0 = Successfully completed until blue line
		 * 1 = Lossed Line => refind line and restart following
		 * 2 = Bumped into box => start box avoidance and restart following
		 * 3 = User interrupt, end program
		 */
		
		//start driving forward
		while(true) {
			int exitCode = followLine();
			switch (exitCode) {
				case 0:
					System.out.println("Finished line following");
					move.stop();
					return;
				case 3:
					refindLine();
					break;
				case 2:
					avoidBox();
					break;
				case 1:
					System.out.println("User interrupt");
					move.stop();
					return;
			}
		}
		
		
		//if total loss to high jump to refind line

	}
	
	int followLine() {
		int error = 0;
		//! Error not in while but total error!
		while(true) {
			//if bumped => return 2
			if(bumper.anyBumbed()) return 2;
			
			//if blue line => return 0
			//TODO add blue line
			
			//if button pressed => return 1
			if(Button.ESCAPE.isDown()) return 3;
			
			//if total loss too big => return 3
			
			//measure loss and adjust for it
			
			move.setMotorRotation(error, 150f);
			//summ total loss
			
			//reset total loss if still on line

			move.forward();
		}
		
	}
	
	void refindLine() {
		System.out.println("Refinding line");
	}
	
	void avoidBox() {
		System.out.println("Avoiding box");
		move.moveByDistance(-7);
		move.turnLeft90();
		move.moveByDistance(17);
		move.turnRight90();
		move.moveByDistance(38);
		move.turn(-65);
		move.moveByDistance(20);
	}
	
	float calculateError() {
		//get grey value and calculate error
		float value = sensor.getGreyScale();
		float error = (sensor.getBorderValue() - value) * ERROR_FACTOR;
		
		if(value > 0.08) {
			//TODO reset total error
			
			//tTimeout.resetCounter();
		}
		return error;
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
