package pe.com.example.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationConfig {
    private final SendGridConfig sendGridConfig;
    private final TwilioConfig twilioConfig;
    private final FcmConfig fcmConfig;
    @Builder.Default
    private final int asyncPoolSize = Runtime.getRuntime().availableProcessors();
}