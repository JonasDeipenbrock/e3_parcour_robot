package levelSolver;

import lejos.hardware.Button;
import wrappers.ColorSensor;

public class Calibration implements ILevelSolver {
	
	ColorSensor colorSensor;
	
	public Calibration() {
		colorSensor = ColorSensor.getInstance();
	}

	@Override
	public void run() {
		init();
	}
	
	public void init() {
		System.out.println("Set on black");
		Button.waitForAnyPress();
		float dataBlack = colorSensor.getGreyScale();
		System.out.println(String.format("black: %f", dataBlack));
		System.out.println("Set on white");
		Button.waitForAnyPress();
		float dataWhite = colorSensor.getGreyScale();
		System.out.println(String.format("white: %f", dataWhite));
		System.out.println("Press any button to continue");
		Button.waitForAnyPress();
		
		colorSensor.blackValue = dataBlack;
		colorSensor.whiteValue = dataWhite;
	}

	@Override
	public void interrupt() {

	}

}
