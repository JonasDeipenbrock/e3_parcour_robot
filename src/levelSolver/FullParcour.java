package levelSolver;

import wrappers.ExitCode;

public class FullParcour implements ILevelSolver {

	@Override
	public ExitCode run() {
		new LineFollowingV2().run();
		new FindAndPush().run();
		new BridgeCrossing().run();
		new FindCrosses().run();
		return ExitCode.SUCCESSFULL;
	}
}
