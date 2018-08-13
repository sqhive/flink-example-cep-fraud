package london.sqhive.flink.examples.cep.conditions;

import london.sqhive.flink.examples.cep.models.Event;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;

public class DebitConditionEqualValue
    extends IterativeCondition<Event> {

    @Override
    public boolean filter(Event event, Context<Event> ctx)
        throws Exception {
        Event previous = ctx.getEventsForPattern("start").iterator().next();

        System.out.println(previous);

        return event.getType() == previous.getType() &&
            event.getAmount().equals(previous.getAmount());
    }
}
