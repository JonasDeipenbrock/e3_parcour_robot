package e3base;

import lejos.hardware.lcd.LCD;
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

	private static Movement movement;
	public static void main(String[] args) {
		// Initialize all sensors
		try  {
			ColorSensor.getInstance();
			UltrasonicSensor.getInstance();
			BumperSensor.getInstance();
			movement = Movement.getInstance();
			UltrasonicPosition.getInstance();
		} catch (Exception e) {
			// sometimes Lejos just fails. Try again
			main(args);
			return;
		}
		runLoop();
		endProgram();
	}
	
	public static void runLoop() {
		while (true) {
			LCD.clearDisplay();
			// Draws the basic menu and wait for the user to select a value
			String[] items = Algorithms.getNames();
			StartMenu startMenu = new StartMenu();
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
				movement.stop();
				System.out.println("Finished part");
				Delay.msDelay(5000);
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
