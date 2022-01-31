package levelSolver;

import lejos.utility.Delay;
import wrappers.ExitCode;
import wrappers.Movement;

public class FullParcour implements ILevelSolver {

	@Override
	public ExitCode run() {
		Movement movement = Movement.getInstance();
		movement.turnLeft90();
		movement.turnRight90();
		movement.turnLeft90();
		movement.turnLeft90();
		movement.turnRight90();
		movement.turnRight90();
		Delay.msDelay(100);
		movement.turnLeft90();
		Delay.msDelay(1000);
		movement.turnRight90();
		/*
		ExitCode ex;
		ex = new LineFollowingV2().run();
		if(ex != ExitCode.SUCCESSFULL) return ex;
		ex = new FindAndPush().run();
		if(ex != ExitCode.SUCCESSFULL) return ex;
		ex = new BridgeCrossing().run();
		if(ex != ExitCode.SUCCESSFULL) return ex;
		ex = new FindCrosses().run();
		if(ex != ExitCode.SUCCESSFULL) return ex;
		*/
		return ExitCode.SUCCESSFULL;

	}
}
