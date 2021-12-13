package wrappers;

import e3base.Configuration;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.Color;

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

    public static ColorSensor getInstance() {
        if(singleton == null) {
            singleton = new ColorSensor();
        }

        return singleton;
    }
    
    public boolean checkBlue() {
    	return getColor() == Color.BLUE;
    }
    
    public boolean checkWhite() {
    	float grey = getGreyScale();
    	return grey > 0.1f;
    	
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

    public int getColor() {
        int colorId = colorSensor.getColorID();
        switch (colorId) {
            case Color.BLACK:
            case Color.NONE:
            case Color.BROWN:
            case Color.DARK_GRAY:
                return Color.BLACK;
            case Color.WHITE:
            case Color.YELLOW:
            case Color.LIGHT_GRAY:
                return Color.WHITE;
            case Color.RED:
            case Color.MAGENTA:
            case Color.PINK:
            case Color.ORANGE:
                return Color.RED;
            case Color.BLUE:
            case Color.CYAN:
                return Color.BLUE;
        }
        return colorId;
    }

    public static void close() {
        if (singleton != null) {
            singleton.colorSensor.close();
            singleton = null;
        }
    }
}