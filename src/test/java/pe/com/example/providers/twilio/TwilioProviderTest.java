package pe.com.example.providers.twilio;

import org.junit.jupiter.api.Test;
import pe.com.example.api.NotificationResult;
import pe.com.example.api.SmsNotification;
import pe.com.example.config.TwilioConfig;

import static org.junit.jupiter.api.Assertions.*;

class TwilioProviderTest {

    @Test
    void shouldSendSmsSuccessfully() {
        TwilioConfig config = TwilioConfig.builder()
                .accountSid("fake-sid")
                .authToken("fake-token")
                .fromNumber("+123456789")
                .build();
        TwilioProvider provider = new TwilioProvider(config);

        SmsNotification sms = SmsNotification.builder()
                .phoneNumber("+987654321")
                .body("Hello SMS")
                .build();

        NotificationResult result = provider.send(sms);
        assertTrue(result.isSuccess());
        assertEquals("Twilio", result.getProviderName());
        assertEquals("SMS", result.getChannelType());
    }

    @Test
    void shouldFailForInvalidPhone() {
        TwilioConfig config = TwilioConfig.builder()
                .accountSid("fake-sid")
                .authToken("fake-token")
                .fromNumber("+123456789")
                .build();
        TwilioProvider provider = new TwilioProvider(config);

        SmsNotification sms = SmsNotification.builder()
                .phoneNumber("123") // inválido
                .body("Hello SMS")
                .build();

        NotificationResult result = provider.send(sms);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Invalid phone number"));
    }
}