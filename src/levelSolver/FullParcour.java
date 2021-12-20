package levelSolver;

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
		move.setToMaxSpeed();
		move.forwardUntil(new Callable<Boolean>() {
			public Boolean call() {
				return Button.ENTER.isDown();
			}
		});
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub

	}

}
