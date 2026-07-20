package pe.com.example.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TwilioConfig extends ProviderConfig {
    private final String accountSid;
    private final String authToken;
    private final String fromNumber;

    public TwilioConfig(String accountSid, String authToken, String fromNumber) {
        super("Twilio");
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.fromNumber = fromNumber;
    }
}
