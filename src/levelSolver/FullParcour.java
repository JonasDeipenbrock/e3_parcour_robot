package levelSolver;

import drivingConditions.OrCondition;
import drivingConditions.BumperCondition;
import drivingConditions.TimeoutCondition;
import lejos.utility.Delay;
import wrappers.Movement;

public class FullParcour implements ILevelSolver {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Movement move = Movement.getInstance();
		move.setSpeed(500);
		move.setToMaxAcc();
		move.forwardUntil(new OrCondition(new TimeoutCondition(5000), new BumperCondition()));
	}
}
