package london.sqhive.flink.examples.cep.functions;

import london.sqhive.flink.examples.cep.enums.EventType;
import london.sqhive.flink.examples.cep.models.Event;
import org.apache.flink.api.common.functions.MapFunction;

public class EventMap
    implements MapFunction<String, Event> {

    @Override
    public Event map(String in) {
        String parts[] = in.split(",");

        return Event
            .builder()
            .type(EventType.valueOf(parts[0]))
            .merchant(parts[1])
            .amount(Double.valueOf(parts[2]))
            .build();
    }
}
