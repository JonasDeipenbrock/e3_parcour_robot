package levelSolver;

import e3base.Helper;
import lejos.hardware.Button;
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
		
		// Move forward until distance goes above threshold
		while (helper.checkLoop(false)) {
			float distanceLeft = uSensor.getDistance();
			if(distanceLeft > 0.4f) break;
			//we want to reach 0.3m to the right ideally
			float offset = (0.28f - distanceLeft) * -1000;	//error is difference from ideal wall point
			movement.setMotorRotation(offset);
			movement.forward();
			System.out.println(String.format("%f, offset: %f", distanceLeft, offset));
		}
		
		movement.stop();
		
//		// Move forward until distance drops bellow threshold again -> Box found
//		Delay.msDelay(100);
//		while (uSensor.getDistance() > 0.3f) {
//			if (Button.ENTER.isDown()) return;
//			Delay.msDelay(100); //TODO: Check if needed
//		}
//		
//		//move a bit further -> in front of box
//		Delay.msDelay(500);
//		movement.stop();
//		movement.turnRight90();
//		
//		// Move forward until Motors stop or push sensor -> Push box against wall
//		movement.forward();
//		while (!movement.motorStalles()) {
//			if (Button.ENTER.isDown()) return;
//			Delay.msDelay(100); //TODO: Check if needed
//		}
//		
//		//Move around box by one side
//		movement.stop();
//		movement.turnRight90();
//		movement.moveByDistance(20);
//		movement.turnLeft90();
//		movement.moveByDistance(20);
//		movement.turnLeft90();
//		
//		// Push box against second wall -> Box in corner
//		movement.forward();
//		while (!movement.motorStalles()) {
//			if (Button.ENTER.isDown()) return;
//			Delay.msDelay(100); //TODO: Check if needed
//		}
//		
//		//Drive to exit
//		movement.stop();
//		movement.turnLeft90();
//		movement.moveByDistance(20);
//		movement.turnLeft90();
//		movement.forward();
//		while (!colorSensor.checkBlue()) {	//stop on blue line
//			if (Button.ENTER.isDown()) return;
//			Delay.msDelay(100); //TODO: Check if needed
//		}
//		movement.stop();
	}

	@Override

	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
