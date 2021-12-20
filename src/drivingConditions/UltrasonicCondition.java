package drivingConditions;

import wrappers.UltrasonicSensor;

import java.util.Comparator;

public class UltrasonicCondition implements IDrivingCondition {

    private UltrasonicSensor ultrasonicSensor;
    private ComparisonMethod comp;
    private float threshold;

    public UltrasonicCondition(ComparisonMethod comp, float threshold) {
        this.comp = comp;
        this.threshold = threshold;
        this.ultrasonicSensor = UltrasonicSensor.getInstance();
    }

    @Override
    public Boolean call() throws Exception {
        float distance = ultrasonicSensor.getDistance();
        switch (this.comp) {
            case LESS:
                return distance <= threshold;
            case GREATER:
                return distance >= threshold;
            default:
                return false;
        }
    }
}
