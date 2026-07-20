package pe.com.example.core;

import org.junit.jupiter.api.Test;
import pe.com.example.api.EmailNotification;
import pe.com.example.api.NotificationResult;
import pe.com.example.api.PushNotification;
import pe.com.example.api.SmsNotification;
import pe.com.example.config.FcmConfig;
import pe.com.example.config.NotificationConfig;
import pe.com.example.config.SendGridConfig;
import pe.com.example.config.TwilioConfig;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTest {

    @Test
    void shouldSendEmailViaService() {
        SendGridConfig sgConfig = SendGridConfig.builder()
                .apiKey("fake")
                .fromEmail("from@test.com")
                .build();
        NotificationConfig config = NotificationConfig.builder()
                .sendGridConfig(sgConfig)
                .build();
        NotificationService service = new NotificationService(config);

        EmailNotification email = EmailNotification.builder()
                .to("test@example.com")
                .subject("Test")
                .body("Body")
                .build();

        NotificationResult result = service.send("EMAIL", email);
        assertTrue(result.isSuccess());
        assertEquals("SendGrid", result.getProviderName());
        service.shutdown();
    }

    @Test
    void shouldSendSmsViaService() {
        TwilioConfig twilioConfig = TwilioConfig.builder()
                .accountSid("fake")
                .authToken("fake")
                .fromNumber("+123456789")
                .build();
        NotificationConfig config = NotificationConfig.builder()
                .twilioConfig(twilioConfig)
                .build();
        NotificationService service = new NotificationService(config);

        SmsNotification sms = SmsNotification.builder()
                .phoneNumber("+987654321")
                .body("Hello")
                .build();

        NotificationResult result = service.send("SMS", sms);
        assertTrue(result.isSuccess());
        assertEquals("Twilio", result.getProviderName());
        service.shutdown();
    }

    @Test
    void shouldSendPushViaService() {
        FcmConfig fcmConfig = FcmConfig.builder()
                .serviceAccountKeyPath("/fake/path.json")
                .build();
        NotificationConfig config = NotificationConfig.builder()
                .fcmConfig(fcmConfig)
                .build();
        NotificationService service = new NotificationService(config);

        PushNotification push = PushNotification.builder()
                .deviceToken("valid-token")
                .title("Title")
                .body("Body")
                .build();

        NotificationResult result = service.send("PUSH", push);
        assertTrue(result.isSuccess());
        assertEquals("FCM", result.getProviderName());
        service.shutdown();
    }

    @Test
    void shouldHandleAsyncSending() {
        SendGridConfig sgConfig = SendGridConfig.builder()
                .apiKey("fake")
                .fromEmail("from@test.com")
                .build();
        NotificationConfig config = NotificationConfig.builder()
                .sendGridConfig(sgConfig)
                .asyncPoolSize(2)
                .build();
        NotificationService service = new NotificationService(config);

        EmailNotification email = EmailNotification.builder()
                .to("test@example.com")
                .subject("Async Test")
                .body("Body")
                .build();

        var future = service.sendAsync("EMAIL", email);
        NotificationResult result = future.join();
        assertTrue(result.isSuccess());
        service.shutdown();
    }
}