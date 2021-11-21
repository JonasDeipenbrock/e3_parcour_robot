package wrappers;

import e3base.Configuration;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class ColorSensor {

    static private ColorSensor singleton;
    private EV3ColorSensor colorSensor;
    private SampleProvider colorSampleProvider;
    private float[] data = new float[3];
    
    public float whiteValue = 0.2f;
    public float blackValue = 0.01f;
    public float getBorderValue() {
    	return (whiteValue + blackValue) / 2;
    }

    private ColorSensor() {
    	colorSensor = new EV3ColorSensor(Configuration.colorSensorPort);
    	colorSensor.setCurrentMode(2);
    	colorSampleProvider = colorSensor.getRGBMode();
    	colorSampleProvider.fetchSample(data, 0);
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
    
    public float getGreyScale() {
    	colorSampleProvider.fetchSample(data, 0);
    	float grey = (data[0] + data[1] + data[2]) / 3;
    	return grey;
    }

    public float[] getColorData() {
    	colorSampleProvider.fetchSample(data, 0);
        return data;
    }

    public static void close() {
        if (singleton != null) {
            singleton.colorSensor.close();
            singleton = null;
        }
    }
}