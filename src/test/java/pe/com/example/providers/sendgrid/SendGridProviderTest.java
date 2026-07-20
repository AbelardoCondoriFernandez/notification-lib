package pe.com.example.providers.sendgrid;

import org.junit.jupiter.api.Test;
import pe.com.example.api.EmailNotification;
import pe.com.example.api.NotificationResult;
import pe.com.example.config.SendGridConfig;

import static org.junit.jupiter.api.Assertions.*;

class SendGridProviderTest {

    @Test
    void shouldSendEmailSuccessfully() {
        SendGridConfig config = SendGridConfig.builder()
                .apiKey("fake-key")
                .fromEmail("from@example.com")
                .build();
        SendGridProvider provider = new SendGridProvider(config);

        EmailNotification email = EmailNotification.builder()
                .to("test@example.com")
                .subject("Test Subject")
                .body("Test Body")
                .build();

        NotificationResult result = provider.send(email);
        assertTrue(result.isSuccess());
        assertEquals("SendGrid", result.getProviderName());
        assertEquals("EMAIL", result.getChannelType());
        assertEquals("Notification sent successfully", result.getMessage());
    }

    @Test
    void shouldFailForInvalidEmail() {
        SendGridConfig config = SendGridConfig.builder()
                .apiKey("fake-key")
                .fromEmail("from@example.com")
                .build();
        SendGridProvider provider = new SendGridProvider(config);

        EmailNotification email = EmailNotification.builder()
                .to("invalid-email") // inválido
                .subject("Test Subject")
                .body("Test Body")
                .build();

        NotificationResult result = provider.send(email);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Invalid email address"));
    }

    @Test
    void shouldFailForEmptySubject() {
        SendGridConfig config = SendGridConfig.builder()
                .apiKey("fake-key")
                .fromEmail("from@example.com")
                .build();
        SendGridProvider provider = new SendGridProvider(config);

        EmailNotification email = EmailNotification.builder()
                .to("test@example.com")
                .subject("")
                .body("Test Body")
                .build();

        NotificationResult result = provider.send(email);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("cannot be empty"));
    }
}