package pe.com.example.api;

import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Getter
@Builder
public class PushNotification implements Notification {
    private final String deviceToken; // equivale a "to"
    private final String title;
    private final String body;
    @Builder.Default
    private final Map<String, Object> metadata = Map.of();

    @Override
    public String getTo() {
        return deviceToken;
    }
}
