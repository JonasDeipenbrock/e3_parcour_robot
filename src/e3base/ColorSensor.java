package e3base;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class ColorSensor {

    static private ColorSensor singleton;
    private EV3ColorSensor colorSensor;
    private SampleProvider colorSampleProvider;
    private float[] data = new float[4];

    private ColorSensor() {
    	colorSensor = new EV3ColorSensor(Configuration.colorSensorPort);
    	colorSensor.setCurrentMode(2);
    	colorSampleProvider = colorSensor.getRGBMode();
    }
    
    public void setMode(int modeID) {
    	colorSensor.setCurrentMode(modeID);
    }

    public static ColorSensor getInstance() {
        if(singleton == null) {
            singleton = new ColorSensor();
        }

        return singleton;
    }

    public float[] getColorData() {
    	colorSampleProvider.fetchSample(data, 0);
    	//colorSensor.fetchSample(data, 0);
        //ultrasonicSampleProvider.fetchSample(data, 0);
    	//System.out.println(data);
        return data;
    }

    public static void close() {
        if (singleton != null) {
            singleton.colorSensor.close();
            singleton = null;
        }
    }
}