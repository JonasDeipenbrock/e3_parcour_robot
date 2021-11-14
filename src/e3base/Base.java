package e3base;

import java.util.Arrays;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import levelSolver.Algorithms;
import levelSolver.BridgeCrossing;
import levelSolver.ILevelSolver;
import levelSolver.LineFollowing;
import menu.StartMenu;

public class Base {
	//Sensors
	public static EV3TouchSensor leftTouch = new EV3TouchSensor(Configuration.leftTouchSensorPort);
	public static EV3TouchSensor rightTouch = new EV3TouchSensor(Configuration.rightTouchSensorPort);
	public static EV3ColorSensor colorSensor = new EV3ColorSensor(Configuration.colorSensorPort);

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
		
		
		// Draws the basic menu and wait for the user to select a value
		String[] items = Algorithms.getNames();
		StartMenu startMenu = new StartMenu();
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
		
		//TODO Remove this loop later when the algorithms have their own exit states
		while (Button.ESCAPE.isUp()) {
		}
		
		Movement.close();
		UltrasonicPosition.close();
		UltrasonicSensor.close();
		leftTouch.close();
		rightTouch.close();
		colorSensor.close();
	}
	
	public static void runLoop() {
		
	}

}
