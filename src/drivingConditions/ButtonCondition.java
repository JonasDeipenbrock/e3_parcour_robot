package drivingConditions;

import lejos.hardware.Button;

public class ButtonCondition implements IDrivingCondition {

    @Override
    public Integer call() throws Exception {
        return Button.ENTER.isDown() ? 1 : 0;
    }
}
