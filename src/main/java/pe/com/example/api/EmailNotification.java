package pe.com.example.api;

import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Getter
@Builder
public class EmailNotification implements Notification {
    private final String to;
    private final String subject;
    private final String body;
    private final String from; // opcional, si no está en la config
    @Builder.Default
    private final Map<String, Object> metadata = Map.of();
}