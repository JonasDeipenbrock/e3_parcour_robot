package e3base;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;
import levelSolver.Algorithms;
import levelSolver.ILevelSolver;
import menu.StartMenu;

public class Base {
	//Sensors
	public static EV3TouchSensor leftTouch = new EV3TouchSensor(Configuration.leftTouchSensorPort);
	public static EV3TouchSensor rightTouch = new EV3TouchSensor(Configuration.rightTouchSensorPort);

	public static void main(String[] args) {
		
//		float[] sensorDataLeft = new float[1];
//		float[] sensorDataRight = new float[2];
		//Movement move = Movement.getInstance();
		 
		//Move the pilot to its own class and asbtract its movement methods for our uses in the algorithms
//		DifferentialPilot robot = new DifferentialPilot(
//				Configuration.wheelDiameter, 
//				Configuration.trackWidth, 
//				left, 
//				right, 
//				true);
//		robot.setAcceleration(4000);
////		robot.setTravelSpeed(20);
//		robot.setRotateSpeed(180);
		
		while (Button.ESCAPE.isUp()) {
			// Draws the basic menu and wait for the user to select a value
			String[] items = Algorithms.getNames();
			StartMenu startMenu = new StartMenu();
			Delay.msDelay(1000);
			startMenu.drawMenu(items);
			int selectedMode = startMenu.selectMenuElement();
			startMenu.clearMenu();
			Algorithms value = Algorithms.values()[selectedMode];
			try {
				ILevelSolver executingAlgo = value.getLevelSolver().newInstance();
				executingAlgo.run();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				System.exit(1);
			}
		
			// -> All algorithms should run until the user interrupts via button press
		}
		
		Movement.close();
		UltrasonicPosition.close();
		UltrasonicSensor.close();
		leftTouch.close();
		rightTouch.close();
		//colorSensor.close();
		ColorSensor.close();
	}
	
	public static void runLoop() {
		
	}

}
