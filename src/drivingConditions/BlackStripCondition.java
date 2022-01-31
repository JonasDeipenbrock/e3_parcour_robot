package drivingConditions;

import wrappers.ColorSensor;

public class BlackStripCondition implements IDrivingCondition{

    private ColorSensor colorSensor;
    private int blackCount = 0;
    private int samplesRequired;
    
    
    
    public BlackStripCondition(int samplesRequired) {
        colorSensor = ColorSensor.getInstance();
        this.samplesRequired = samplesRequired;
    }
    
    public BlackStripCondition() {
        this(1);
    }

    @Override
    public Integer call() throws Exception {
    	if (!colorSensor.checkWhite()) {
    		blackCount++;
    	}
    	else {
    		blackCount = 0;
    	}
        return blackCount >= samplesRequired ? 1 : 0;
    }
}
