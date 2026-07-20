package pe.com.example.api;

import lombok.Builder;
import lombok.Getter;
import java.time.Instant;

@Getter
@Builder
public class NotificationResult {
    private final boolean success;
    private final String message;
    private final String providerName;
    private final String channelType;
    private final Instant timestamp;
    private final Throwable cause;

    public static NotificationResult success(String provider, String channel) {
        return NotificationResult.builder()
                .success(true)
                .message("Notification sent successfully")
                .providerName(provider)
                .channelType(channel)
                .timestamp(Instant.now())
                .build();
    }

    public static NotificationResult failure(String provider, String channel, String errorMessage, Throwable cause) {
        return NotificationResult.builder()
                .success(false)
                .message(errorMessage)
                .providerName(provider)
                .channelType(channel)
                .timestamp(Instant.now())
                .cause(cause)
                .build();
    }
}