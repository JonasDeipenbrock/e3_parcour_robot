package drivingConditions;

import wrappers.ColorSensor;

public class BlueStripCondition implements IDrivingCondition{

    ColorSensor colorSensor;
    public BlueStripCondition() {
        colorSensor = ColorSensor.getInstance();
    }

    @Override
    public Integer call() throws Exception {
        return colorSensor.checkBlue() ? 1 : 0;
    }
}
