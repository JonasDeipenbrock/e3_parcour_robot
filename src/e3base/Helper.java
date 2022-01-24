package e3base;

import lejos.hardware.Button;
import wrappers.BumperSensor;
import wrappers.ColorSensor;

public class Helper {
	static private Helper singleton;
	ColorSensor sensor;
	BumperSensor bumper;
	
	private Helper() {
		sensor = ColorSensor.getInstance();
		bumper = BumperSensor.getInstance();
	}
	
	public static Helper getInstance() {
        if(singleton == null) {
            singleton = new Helper();
        }

        return singleton;
    }
	
	/**
	 * Check if break condition is already met
	 * @return
	 */
	public boolean checkLoop(boolean bumperCheck, boolean blueMode) {
		if(Button.ESCAPE.isDown()) {
			System.out.println("Button Notaus");
			return false;
		};
		//this should not be checked here cause we cant drive around the box this way
		if(bumperCheck && bumper.anyBumbed()) return false;
		if(blueMode && sensor.checkBlue()) {
			System.out.println("Exited cause of blue");
			return false;
		}
		return true;
	}
}
