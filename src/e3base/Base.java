package e3base;

import java.util.Arrays;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;
import levelSolver.Algorithms;
import levelSolver.BridgeCrossing;
import menu.StartMenu;

public class Base {
	//Sensors
	public static EV3TouchSensor leftTouch = new EV3TouchSensor(Configuration.leftTouchSensorPort);
	public static EV3TouchSensor rightTouch = new EV3TouchSensor(Configuration.rightTouchSensorPort);
	public static EV3ColorSensor colorSensor = new EV3ColorSensor(Configuration.colorSensorPort);
	public static EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(Configuration.ultrasonicSensorPort);

	public static void main(String[] args) {
		/*
		float[] sensorDataLeft = new float[1];
		float[] sensorDataRight = new float[2];
		Movement move = Movement.getInstance();
		 
		//Move the pilot to its own class and asbtract its movement methods for our uses in the algorithms
		DifferentialPilot robot = new DifferentialPilot(
				Configuration.wheelDiameter, 
				Configuration.trackWidth, 
				left, 
				right, 
				true);
		//! Test runs, remove later on
		System.out.println("Press any button!");
		Button.waitForAnyPress();
		robot.setAcceleration(4000);
		robot.setTravelSpeed(20);
		robot.setRotateSpeed(180);
		System.out.println("Going forwards");
		robot.travel(20);
		robot.rotate(180);
		robot.rotate(-1000);
		while(robot.isMoving())Thread.yield();
		System.out.println("Stopped");
		
		ipMotor.forward();
		irMotor.rotate(90);
		Delay.msDelay(600);
		Delay.msDelay(1000);
		irMotor.rotate(-90);
		ipMotor.stop();
		Delay.msDelay(1000);
		ipMotor.backward();
		Delay.msDelay(600);
		ipMotor.stop(false);
		
		try {
			move.turnLeft90();
			Thread.sleep(1000);
			move.turnRight90();
			ipMotor.forward();
			Thread.sleep(1000);
			ipMotor.stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		
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
				System.out.println("Not implemented");
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
	}
	
	public static void runLoop() {
		
	}

}
