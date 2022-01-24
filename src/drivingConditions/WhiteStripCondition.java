package drivingConditions;

import wrappers.ColorSensor;

public class WhiteStripCondition implements IDrivingCondition{

    private ColorSensor colorSensor;
    private int whiteCount = 0;
    private int samplesRequired;
    
    
    
    public WhiteStripCondition(int samplesRequired) {
        colorSensor = ColorSensor.getInstance();
        this.samplesRequired = samplesRequired;
    }
    
    public WhiteStripCondition() {
        this(1);
    }

    @Override
    public Integer call() throws Exception {
    	if (colorSensor.checkWhite()) {
    		whiteCount++;
    	}
    	else {
    		whiteCount = 0;
    	}
        return whiteCount >= samplesRequired ? 1 : 0;
    }
}
