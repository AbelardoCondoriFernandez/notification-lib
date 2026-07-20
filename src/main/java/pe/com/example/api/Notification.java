package pe.com.example.api;

import java.util.Map;

public interface Notification {
    String getTo();
    String getBody();
    default Map<String, Object> getMetadata() {
        return Map.of();
    }
}