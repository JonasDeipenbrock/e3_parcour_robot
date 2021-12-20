package drivingConditions;

public class TimeoutCondition implements IDrivingCondition {

    private long startTime;
    private long timeout;

    public TimeoutCondition(long timeout) {
        this.timeout = timeout;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public Boolean call() throws Exception {
        long timeSinceStart = System.currentTimeMillis() - startTime;
        return timeSinceStart >= timeout;
    }
}
