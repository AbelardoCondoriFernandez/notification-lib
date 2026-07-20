package pe.com.example.providers.fcm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.com.example.api.Notification;
import pe.com.example.api.NotificationProvider;
import pe.com.example.api.NotificationResult;
import pe.com.example.api.PushNotification;
import pe.com.example.config.FcmConfig;
import pe.com.example.exceptions.ValidationException;
import pe.com.example.validation.DeviceTokenValidator;

public class FcmProvider implements NotificationProvider {
    private static final Logger log = LoggerFactory.getLogger(FcmProvider.class);
    private final FcmConfig config;
    private final DeviceTokenValidator tokenValidator = new DeviceTokenValidator();

    public FcmProvider(FcmConfig config) {
        this.config = config;
    }

    @Override
    public NotificationResult send(Notification notification) {
        if (!(notification instanceof PushNotification)) {
            return NotificationResult.failure(
                    getProviderName(),
                    getChannelType(),
                    "Notification is not a PushNotification",
                    null
            );
        }
        PushNotification push = (PushNotification) notification;

        try {
            // Validar device token
            tokenValidator.validate(push.getDeviceToken());
            // Validar título y cuerpo
            if (push.getTitle() == null || push.getTitle().trim().isEmpty()) {
                throw new ValidationException("Push title cannot be empty");
            }
            if (push.getBody() == null || push.getBody().trim().isEmpty()) {
                throw new ValidationException("Push body cannot be empty");
            }
        } catch (ValidationException e) {
            return NotificationResult.failure(getProviderName(), getChannelType(), e.getMessage(), e);
        }

        // Simulación de envío
        log.info("Sending Push notification via FCM:");
        log.info("  Device Token: {}", push.getDeviceToken());
        log.info("  Title: {}", push.getTitle());
        log.info("  Body: {}", push.getBody());

        // Simular éxito
        return NotificationResult.success(getProviderName(), getChannelType());
    }

    @Override
    public String getProviderName() {
        return "FCM";
    }

    @Override
    public String getChannelType() {
        return "PUSH";
    }
}
