package e3base;

import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;
import levelSolver.Algorithms;
import levelSolver.ILevelSolver;
import menu.StartMenu;
import wrappers.BumperSensor;
import wrappers.ColorSensor;
import wrappers.Movement;
import wrappers.UltrasonicPosition;
import wrappers.UltrasonicSensor;

public class Base {

	public static void main(String[] args) {
		runLoop();
		endProgram();
	}
	
	public static void runLoop() {
		while (true) {
			LCD.clearDisplay();
			// Draws the basic menu and wait for the user to select a value
			String[] items = Algorithms.getNames();
			StartMenu startMenu = new StartMenu();
			Delay.msDelay(1000);
			startMenu.drawMenu(items);
			int selectedMode = startMenu.selectMenuElement();
			if(selectedMode == -1) {
				break;
			}
			startMenu.clearMenu();
			Algorithms value = Algorithms.values()[selectedMode];
			try {
				ILevelSolver executingAlgo = value.getLevelSolver().newInstance();
				executingAlgo.run();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public static void endProgram() {
		Movement.close();
		UltrasonicPosition.close();
		UltrasonicSensor.close();
		BumperSensor.close();
		ColorSensor.close();
	}

}
