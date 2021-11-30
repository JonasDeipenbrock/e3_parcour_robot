package levelSolver;

import e3base.Helper;
import lejos.utility.Delay;
import wrappers.ColorSensor;
import wrappers.Movement;
import wrappers.UltrasonicPosition;
import wrappers.UltrasonicSensor;

public class FindAndPush implements ILevelSolver {

	@Override
	public void run() {
		UltrasonicPosition uPosition = UltrasonicPosition.getInstance();
		Movement movement = Movement.getInstance();
		UltrasonicSensor uSensor = UltrasonicSensor.getInstance();
		ColorSensor colorSensor = ColorSensor.getInstance();
		Helper helper = Helper.getInstance();

		// Move Ultrasonic Sensor in to upright position
		uPosition.moveUP();
		
		//move to right wall by turning 45 degree
		//turn straight again
		
		//Kurve hardcoden
		//ideale distanz zur wand sind etwa 5 cm, also linke entfernung ungefähr 25cm
		
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
		System.out.println("Moving to box");
		while(helper.checkLoop(false, false)) {
			float distanceLeft = uSensor.getDistance();
			if(distanceLeft < 0.4f) break;
			movement.setToMaxSpeed();
			movement.forward();
		}
//		//move a bit further -> in front of box
		Delay.msDelay(480);
		movement.stop();
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
		movement.stop();
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
		movement.stop();
	
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
		}
		movement.stop();
	}

	@Override

	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
