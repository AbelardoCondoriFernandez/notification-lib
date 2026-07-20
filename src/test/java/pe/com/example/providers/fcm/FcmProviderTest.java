package pe.com.example.providers.fcm;

import org.junit.jupiter.api.Test;
import pe.com.example.api.NotificationResult;
import pe.com.example.api.PushNotification;
import pe.com.example.config.FcmConfig;

import static org.junit.jupiter.api.Assertions.*;

class FcmProviderTest {

    @Test
    void shouldSendPushSuccessfully() {
        FcmConfig config = FcmConfig.builder()
                .serviceAccountKeyPath("/fake/path.json")
                .build();
        FcmProvider provider = new FcmProvider(config);

        PushNotification push = PushNotification.builder()
                .deviceToken("valid-token-123")
                .title("Test Title")
                .body("Test Body")
                .build();

        NotificationResult result = provider.send(push);
        assertTrue(result.isSuccess());
        assertEquals("FCM", result.getProviderName());
        assertEquals("PUSH", result.getChannelType());
    }

    @Test
    void shouldFailForEmptyTitle() {
        FcmConfig config = FcmConfig.builder()
                .serviceAccountKeyPath("/fake/path.json")
                .build();
        FcmProvider provider = new FcmProvider(config);

        PushNotification push = PushNotification.builder()
                .deviceToken("valid-token-123")
                .title("")
                .body("Test Body")
                .build();

        NotificationResult result = provider.send(push);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("cannot be empty"));
    }
}