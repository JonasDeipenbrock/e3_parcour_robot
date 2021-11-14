package e3base;

import java.util.Arrays;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import levelSolver.Algorithms;
import levelSolver.BridgeCrossing;
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
//		String[] items = {"Full course", "Line following", "Find and Push", "Bridge", "Crosses"};
		String[] items = Arrays.toString(Algorithms.values()).replaceAll("^.|.$", "").split(", ");
		StartMenu startMenu = new StartMenu();
		startMenu.drawMenu(items);
		int selectedMode = startMenu.selectMenuElement();
		startMenu.clearMenu();
		Algorithms value = Algorithms.values()[selectedMode];
		switch(value) {
			case FULLPARCOUR:
				System.out.println("Not implemented");
				break;
			case LINEFOLLOWING:
				LineFollowing line = new LineFollowing();
				line.run();
				break;
			case FINDANDPUSH:
				System.out.println("Not implemented");
				break;
			case BRIDGECROSSING:
				//System.out.println(selectedMode);
				BridgeCrossing bridge = new BridgeCrossing();
				bridge.run();
				break;
			case FINDCROSSES:
				System.out.println("Not implemented");
				break;
		}
		
		//Start specified algorithm
		
		// -> All algorithms should run until the user interrupts via button press
		
		//TODO Remove this loop later when the algorithms have their own exit states
		while (Button.ESCAPE.isUp()) {
		}
		
		Movement move = Movement.getInstance();
		move.close();
		UltrasonicPosition pos = UltrasonicPosition.getInstance();
		pos.close();
		UltrasonicSensor.close();
		leftTouch.close();
		rightTouch.close();
		colorSensor.close();
	}
	
	public static void runLoop() {
		
	}

}
