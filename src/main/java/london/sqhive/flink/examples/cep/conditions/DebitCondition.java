package london.sqhive.flink.examples.cep.conditions;

import london.sqhive.flink.examples.cep.enums.EventType;
import london.sqhive.flink.examples.cep.models.Event;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;

public class DebitCondition
    extends SimpleCondition<Event> {

    @Override
    public boolean filter(Event event) {
        return event.getType() == EventType.DEBIT;
    }
}
