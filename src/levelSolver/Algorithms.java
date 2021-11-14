package levelSolver;

import java.util.Arrays;

public enum Algorithms {
	FULLPARCOUR("Full Parcour", FullParcour.class),
	LINEFOLLOWING("Line Following", LineFollowing.class),
	FINDANDPUSH("Find and Push", FindAndPush.class),
	BRIDGECROSSING("Bridge Crossing", BridgeCrossing.class),
	FINDCROSSES("Find Crosses", FindCrosses.class);

	private final Class<ILevelSolver> levelSolver;
	private final String name;

	Algorithms(String name, Class levelSolver){
		this.name = name;
		this.levelSolver = levelSolver;
	}

	public Class<ILevelSolver> getLevelSolver() {
		return levelSolver;
	}

	public String getName() {
		return name;
	}

	public static Class<ILevelSolver> getClassFromName(String name) {
		for (Algorithms alg : values())
			if (alg.getName().equals(name))
				return alg.getLevelSolver();
		return null;
	}

	public static String[] getNames() {
		return Arrays.stream(Algorithms.values()).map(Algorithms::getName)
				.toArray(String[]::new);
	}
}
