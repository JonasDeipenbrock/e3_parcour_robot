package levelSolver;

import e3base.Helper;
import lejos.utility.Delay;
import wrappers.ColorSensor;
import wrappers.ExitCode;
import wrappers.LEDPattern;
import wrappers.Movement;
import wrappers.StatusIndicator;
import wrappers.UltrasonicSensor;

public class FindAndPush implements ILevelSolver {

	@Override
	public ExitCode run() {
		Movement movement = Movement.getInstance();
		UltrasonicSensor uSensor = UltrasonicSensor.getInstance();
		ColorSensor colorSensor = ColorSensor.getInstance();
		Helper helper = Helper.getInstance();
		StatusIndicator ind = StatusIndicator.getInstance();
		
		ind.setLED(LEDPattern.LED_GREEN_FLASH);
		
		
		//move to right wall by turning 45 degree
		//turn straight again
		int iteration = 1;
		int generations = 50;
		float[] buffer = new float[generations];
		// Move forward until distance goes above threshold
		while (helper.checkLoop(false, false)) {
			//we want to reach 0.25m to the right ideally
			float distanceRight = uSensor.getDistance();
			if(distanceRight > 0.35f) break;
			float offset = (0.22f - distanceRight) * -1000;	//error is difference from ideal wall point
			
			int rest = iteration % generations;
			buffer[rest] = offset;
			
			if(iteration % generations == 0) {			
				float errorSum = 0;
				for (float err: buffer) {
					errorSum += err;
				}
				float medianError = errorSum / generations;
				System.out.println(medianError);
				movement.setMotorRotation(medianError, 500f);
				movement.forward();
			}
			iteration++;
			System.out.println(String.format("%f, offset: %f", distanceRight, offset));
		}
		// Move forward until distance goes above threshold
//		while (helper.checkLoop(false)) {
//			float distanceLeft = uSensor.getDistance();
//			if(distanceLeft > 0.4f) break;
//			//we want to reach 0.3m to the right ideally
//			float offset = (0.28f - distanceLeft) * -1000;	//error is difference from ideal wall point
//			movement.setMotorRotation(offset);
//			movement.forward();
//			System.out.println(String.format("%f, offset: %f", distanceLeft, offset));
//		}
		ind.setLED(LEDPattern.LED_RED);
		
		movement.moveByDistance(10);
		
		System.out.println("Moving to box");
		while(helper.checkLoop(false, false)) {
			float distanceLeft = uSensor.getDistance();
			if(distanceLeft < 0.4f) break;
			movement.setToMaxSpeed();
			movement.forward();
		}
//		//move a bit further -> in front of box
		Delay.msDelay(480);
		movement.stopCorrected();
		movement.turnRight90();
		System.out.println("Pushing the box");
		
		// Move forward until Motors stop or push sensor -> Push box against wall
		movement.forward();
		long start = System.currentTimeMillis();
		int lineCounter = 0;
		while (true) {
			if(!helper.checkLoop(false, false)) {
				System.out.println("Notfall Aus");
				break;
			};
			long current = System.currentTimeMillis();
			if((current - start) > 4000) {
				System.out.println("Met threshold");
				break;
			};
			boolean onLine = colorSensor.checkWhite();
			if(lineCounter == 0 && onLine) {
				System.out.println("First line reached");
				lineCounter++;
			} else if (lineCounter == 1 && !onLine) {
				System.out.println("First line passed");
				lineCounter++;
			} else if (lineCounter == 2 && onLine) {
				System.out.println("Second line reached");
				break;
			}
			System.out.println(String.format("%d, %b", lineCounter, onLine));
			
		}
		Delay.msDelay(500);
		movement.stopCorrected();
		System.out.println("At wall");
		
		//Move around box by one side
		movement.moveByDistance(-2);
		movement.turnRight90();
		movement.moveByDistance(20);
		movement.turnLeft90();
		movement.moveByDistance(15);
		movement.turnLeft90();

		//Move box in corner
		movement.forward();
		long timeout = System.currentTimeMillis();
		while(true) {
			if(!helper.checkLoop(false, false)) {
				System.out.println("Not-Aus");
				break;
			}
			long current = System.currentTimeMillis();
			if((current - timeout) > 3000) {
				System.out.println("Met threshold");
				break;
			};
			if(colorSensor.checkWhite()) {
				System.out.println("In the corner");
				break;
			}
		}
		movement.stopCorrected();
	
		//Drive to exit
		movement.moveByDistance(-10);
		movement.turnLeft90();
		movement.forward();
		while(true) {
			if(!helper.checkLoop(false, false)) break;
			if(colorSensor.checkWhite()) break;
		}
		movement.turnLeft90();
		
		//TODO ausrichtung an wand -> 50cm ideal
		
		movement.forward();
		while (!colorSensor.checkBlue()) {	//stop on blue line
			if (!helper.checkLoop(false, false)) break;
			float distanceRight = uSensor.getDistance();
			//we want to reach 0.5m to the right ideally
			float offset = (0.4f - distanceRight) * -500;	//error is difference from ideal wall point
			movement.setMotorRotation(offset, 500f);
			movement.forward();
		}
		movement.stopCorrected();
		ind.setLED(LEDPattern.LED_RED);
		return ExitCode.SUCCESSFULL;
	}
}
