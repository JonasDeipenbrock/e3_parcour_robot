package wrappers;

import e3base.Configuration;
import lejos.hardware.sensor.EV3TouchSensor;

public class BumperSensor {
    static private BumperSensor singleton;
    public EV3TouchSensor leftTouch;
	public EV3TouchSensor rightTouch;
    private float[] data = new float[2];

    private BumperSensor() {
    	leftTouch = new EV3TouchSensor(Configuration.leftTouchSensorPort);
    	rightTouch = new EV3TouchSensor(Configuration.rightTouchSensorPort);
    }

    public static BumperSensor getInstance() {
        if(singleton == null) {
            singleton = new BumperSensor();
        }

        return singleton;
    }
    
    /**
     * Check if the bumper is active
     * @return true if any bumper currently is bumped
     */
    public boolean anyBumbed() {
    	getBumps();
    	return data[0] == 1 || data[1] == 1;
    }

    /**
     * Fetch a new sample
     * @return array containing the samples, 0 is left, 1 is right
     */
    public float[] getBumps() {
    	leftTouch.fetchSample(data, 0);
    	rightTouch.fetchSample(data, 1);
        return data;
    }

    public static void close() {
        if (singleton != null) {
            singleton.leftTouch.close();
            singleton.rightTouch.close();
            singleton = null;
        }
    }
}
