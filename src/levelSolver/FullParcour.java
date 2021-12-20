package levelSolver;

import drivingConditions.AndCondition;
import drivingConditions.BumperCondition;
import drivingConditions.TimeoutCondition;
import e3base.Helper;
import lejos.hardware.Button;
import lejos.utility.Delay;
import wrappers.Movement;

import java.util.concurrent.Callable;

public class FullParcour implements ILevelSolver {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Movement move = Movement.getInstance();
		move.setSpeed(500);
		move.setToMaxAcc();
		Delay.msDelay(1000);
		move.forwardUntil(new AndCondition(new TimeoutCondition(5000), new BumperCondition()));
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
