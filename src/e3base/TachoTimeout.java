package e3base;

public class TachoTimeout {
	
	float count = 0;
	float initialValue = 0;
	Movement move;
	public TachoTimeout() {
		move = Movement.getInstance();
		int[] initial = move.getTachoCount();
		initialValue = Math.abs((initial[0] + initial[1]) / 2);
	}
	
	public void resetCounter() {
		int[] initial = move.getTachoCount();
		initialValue = Math.abs((initial[0] + initial[1]) / 2);
	}
	
	public float updateCounter() {
		int[] current = move.getTachoCount();
		float currentValue = Math.abs((current[0] + current[1]) / 2);
		count = currentValue - initialValue;
		return count;
	}
}
