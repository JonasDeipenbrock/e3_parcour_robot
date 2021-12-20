package drivingConditions;

import wrappers.BumperSensor;

public class BumperCondition implements IDrivingCondition {

    BumperSensor bumper;

    public BumperCondition() {
        bumper = BumperSensor.getInstance();
    }

    @Override
    public Boolean call() throws Exception {
        return bumper.anyBumbed();
    }
}
