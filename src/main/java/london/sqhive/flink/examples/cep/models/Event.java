package london.sqhive.flink.examples.cep.models;

import lombok.Builder;
import lombok.Data;
import london.sqhive.flink.examples.cep.enums.EventType;

@Data
@Builder
public class Event {
    private EventType type;
    private String merchant;
    private Double amount;
}
