package levelSolver;

import wrappers.ExitCode;

public class FromBridge implements ILevelSolver {

	@Override
	public ExitCode run() {
		ExitCode ex;
		ex = new BridgeCrossing().run();
		if(ex != ExitCode.SUCCESSFULL) return ex;
		ex = new FindCrosses().run();
		if(ex != ExitCode.SUCCESSFULL) return ex;
		return ExitCode.SUCCESSFULL;
	}
}
