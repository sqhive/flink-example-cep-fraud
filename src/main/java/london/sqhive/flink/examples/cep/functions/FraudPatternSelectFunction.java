package london.sqhive.flink.examples.cep.functions;

import london.sqhive.flink.examples.cep.models.Event;
import org.apache.flink.cep.PatternSelectFunction;

import java.util.List;
import java.util.Map;

public class FraudPatternSelectFunction
    implements PatternSelectFunction<Event, String> {

    @Override
    public String select(
        Map<String, List<Event>> pattern
    ) throws Exception {
        return pattern.toString();
    }
}
