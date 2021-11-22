package Colors;

public class Blue {

	final static float TOLERANZ = 0.5f;
	private static final float redValue = 0.0185f;
	private static final float greenValue = 0.07f;
	private static final float blueValue = 0.06f;
	
	static float lowerRed = redValue - redValue * TOLERANZ;
	static float upperRed = redValue + redValue * TOLERANZ;
	static float lowerGreen = greenValue - greenValue * TOLERANZ;
	static float upperGreen = greenValue + greenValue * TOLERANZ;
	static float lowerBlue = blueValue - blueValue * TOLERANZ;
	static float upperBlue = blueValue + blueValue * TOLERANZ;

	public static boolean isColor(float[] data) {
		if(data[0] < lowerRed || data[0] > upperRed) return false;
		if(data[1] < lowerGreen || data[1] > upperGreen) return false;
		if(data[2] < lowerBlue || data[2] > upperBlue) return false;
		return true;
	}

}
