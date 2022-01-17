package drivingConditions;

import wrappers.ColorSensor;

public class BlueStripCondition implements IDrivingCondition{

    private ColorSensor colorSensor;
    private int blueCount = 0;
    private int samplesRequired;
    
    
    
    public BlueStripCondition(int samplesRequired) {
        colorSensor = ColorSensor.getInstance();
        this.samplesRequired = samplesRequired;
    }
    
    public BlueStripCondition() {
        this(1);
    }

    @Override
    public Integer call() throws Exception {
    	if (colorSensor.checkBlue()) {
    		blueCount++;
    	}
    	else {
    		blueCount = 0;
    	}
        return blueCount >= samplesRequired ? 1 : 0;
    }
}
