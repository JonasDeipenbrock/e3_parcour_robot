package wrappers;

import lejos.hardware.LED;
import lejos.hardware.BrickFinder;

public class StatusIndicator {

	static private StatusIndicator singleton;

	private LED led = BrickFinder.getLocal().getLED();

	private StatusIndicator() {

	}

	public static StatusIndicator getInstance() {
		if (singleton == null) {
			singleton = new StatusIndicator();
		}

		return singleton;
	}

    //TODO: Test if patterns in enum are correct
    public void setLED(LEDPattern pattern) {
        led.setPattern(pattern.getValue());
    }
}
