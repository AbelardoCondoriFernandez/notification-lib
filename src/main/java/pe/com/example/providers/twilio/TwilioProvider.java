package pe.com.example.providers.twilio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.com.example.api.Notification;
import pe.com.example.api.NotificationProvider;
import pe.com.example.api.NotificationResult;
import pe.com.example.api.SmsNotification;
import pe.com.example.config.TwilioConfig;
import pe.com.example.exceptions.ValidationException;
import pe.com.example.validation.PhoneValidator;

public class TwilioProvider implements NotificationProvider {
    private static final Logger log = LoggerFactory.getLogger(TwilioProvider.class);
    private final TwilioConfig config;
    private final PhoneValidator phoneValidator = new PhoneValidator();

    public TwilioProvider(TwilioConfig config) {
        this.config = config;
    }

    @Override
    public NotificationResult send(Notification notification) {
        if (!(notification instanceof SmsNotification)) {
            return NotificationResult.failure(
                    getProviderName(),
                    getChannelType(),
                    "Notification is not an SmsNotification",
                    null
            );
        }
        SmsNotification sms = (SmsNotification) notification;

        try {
            // Validar número de teléfono
            phoneValidator.validate(sms.getPhoneNumber());
            // Validar que el body no esté vacío
            if (sms.getBody() == null || sms.getBody().trim().isEmpty()) {
                throw new ValidationException("SMS body cannot be empty");
            }
        } catch (ValidationException e) {
            return NotificationResult.failure(getProviderName(), getChannelType(), e.getMessage(), e);
        }

        // Simulación de envío
        log.info("Sending SMS via Twilio:");
        log.info("  From: {}", config.getFromNumber());
        log.info("  To: {}", sms.getPhoneNumber());
        log.info("  Body: {}", sms.getBody());

        // Simular éxito
        return NotificationResult.success(getProviderName(), getChannelType());
    }

    @Override
    public String getProviderName() {
        return "Twilio";
    }

    @Override
    public String getChannelType() {
        return "SMS";
    }
}
