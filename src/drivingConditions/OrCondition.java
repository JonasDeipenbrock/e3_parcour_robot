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
    public Integer call() throws Exception {
        Iterator<IDrivingCondition> condIterator = conditions.iterator();
        int i = 1;
        while (condIterator.hasNext()) {
            Integer subResult = condIterator.next().call();
            if (subResult != 0) {
                return i;
            };
            i++;
        }
        return 0;
    }
}
