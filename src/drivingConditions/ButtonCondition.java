package drivingConditions;

import lejos.hardware.Button;

public class ButtonCondition implements IDrivingCondition {

    @Override
    public Integer call() throws Exception {
        return Button.ESCAPE.isDown() ? 1 : 0;
    }
}
