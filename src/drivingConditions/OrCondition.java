package drivingConditions;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class OrCondition implements IDrivingCondition {

    Collection<IDrivingCondition> conditions;

    public OrCondition(Collection<IDrivingCondition> conditions) {
        this.conditions = conditions;
    }

    public OrCondition(IDrivingCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    @Override
    public Boolean call() throws Exception {
        Iterator<IDrivingCondition> condIterator = conditions.iterator();
        while (condIterator.hasNext()) {
            boolean subResult = condIterator.next().call();
            if (subResult) {
                return true;
            };
        }
        return false;
    }
}
