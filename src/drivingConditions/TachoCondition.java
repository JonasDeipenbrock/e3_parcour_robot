package drivingConditions;

import wrappers.Movement;

public class TachoCondition implements IDrivingCondition {

    private int tachoDifference;
    private Movement move;
	private int[] initial;

    public TachoCondition(int tachoDifference) {
        this.tachoDifference = tachoDifference;
        this.move = Movement.getInstance();
        this.initial = move.getTachoCount();
    }

    @Override
    public Integer call() throws Exception {
        int[] currentVal = move.getTachoCount();
        int tachoDiff = Math.abs((initial[0] - currentVal[0]) - (initial[1] - currentVal[1]));
        return tachoDiff >= tachoDifference ? 1 : 0;
    }
}
