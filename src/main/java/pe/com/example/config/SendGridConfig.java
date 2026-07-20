package pe.com.example.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendGridConfig extends ProviderConfig {
    private final String apiKey;
    private final String fromEmail;

    public SendGridConfig(String apiKey, String fromEmail) {
        super("SendGrid");
        this.apiKey = apiKey;
        this.fromEmail = fromEmail;
    }
}
