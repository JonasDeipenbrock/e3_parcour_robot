package levelSolver;

import lejos.utility.Delay;
import wrappers.Movement;

public class FullParcour implements ILevelSolver {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Movement move = Movement.getInstance();
		move.setToMaxSpeed();
		move.forward();
		Delay.msDelay(10000);
		move.stop();
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
