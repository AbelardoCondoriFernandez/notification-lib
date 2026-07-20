package pe.com.example.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FcmConfig extends ProviderConfig {
    private final String serviceAccountKeyPath; // o el JSON directamente

    public FcmConfig(String serviceAccountKeyPath) {
        super("FCM");
        this.serviceAccountKeyPath = serviceAccountKeyPath;
    }
}
