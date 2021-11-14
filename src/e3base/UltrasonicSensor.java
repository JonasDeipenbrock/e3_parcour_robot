package e3base;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltrasonicSensor {
    static private UltrasonicSensor singleton;
    private EV3UltrasonicSensor ultrasonicSensor;
    private SampleProvider ultrasonicSampleProvider;
    private boolean upState = false;
    private float[] data = new float[1];

    private UltrasonicSensor() {
        ultrasonicSensor = new EV3UltrasonicSensor(Configuration.ultrasonicSensorPort);
        ultrasonicSensor.enable();
        ultrasonicSampleProvider = ultrasonicSensor.getDistanceMode();
    }

    public static UltrasonicSensor getInstance() {
        if(singleton == null) {
            singleton = new UltrasonicSensor();
        }

        return singleton;
    }

    public float getDistance() {
        ultrasonicSampleProvider.fetchSample(data, 0);
        return data[0];
    }

    public static void close() {
        if (singleton != null) {
            singleton.ultrasonicSensor.close();
        }
    }
}
