package drivingConditions;

import wrappers.ColorSensor;

public class LinesPassedCondition implements IDrivingCondition {

    private int lineCounter = 0;
    private boolean onLine = false;
    private int numberOfLines;
    private ColorSensor colorSensor;

    public LinesPassedCondition(int numberOfLines) {
        this.numberOfLines = numberOfLines;
        colorSensor = ColorSensor.getInstance();
    }

    @Override
    public Integer call() throws Exception {
        boolean currentOnLine = colorSensor.checkWhite();
        if(!onLine && currentOnLine) {
            //reached line
            onLine = currentOnLine;
        } else if (onLine && !currentOnLine) {
            //passed line
            onLine = currentOnLine;
            lineCounter++;
        }
        return lineCounter >= numberOfLines ? 1 : 0;
    }
}
