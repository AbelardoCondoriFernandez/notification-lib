package pe.com.example.providers.sendgrid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.com.example.api.EmailNotification;
import pe.com.example.api.Notification;
import pe.com.example.api.NotificationProvider;
import pe.com.example.api.NotificationResult;
import pe.com.example.config.SendGridConfig;
import pe.com.example.exceptions.ValidationException;
import pe.com.example.validation.EmailValidator;

public class SendGridProvider implements NotificationProvider {
    private static final Logger log = LoggerFactory.getLogger(SendGridProvider.class);
    private final SendGridConfig config;
    private final EmailValidator emailValidator = new EmailValidator();

    public SendGridProvider(SendGridConfig config) {
        this.config = config;
    }

    @Override
    public NotificationResult send(Notification notification) {
        if (!(notification instanceof EmailNotification)) {
            return NotificationResult.failure(
                    getProviderName(),
                    getChannelType(),
                    "Notification is not an EmailNotification",
                    null
            );
        }
        EmailNotification email = (EmailNotification) notification;

        try {
            // Validar email del destinatario
            emailValidator.validate(email.getTo());
            // Validar que subject no sea nulo
            if (email.getSubject() == null || email.getSubject().trim().isEmpty()) {
                throw new ValidationException("Email subject cannot be empty");
            }
        } catch (ValidationException e) {
            return NotificationResult.failure(getProviderName(), getChannelType(), e.getMessage(), e);
        }

        // Simulación de envío
        log.info("Sending email via SendGrid:");
        log.info("  From: {}", config.getFromEmail());
        log.info("  To: {}", email.getTo());
        log.info("  Subject: {}", email.getSubject());
        log.info("  Body: {}", email.getBody());

        // Simular éxito
        return NotificationResult.success(getProviderName(), getChannelType());
    }

    @Override
    public String getProviderName() {
        return "SendGrid";
    }

    @Override
    public String getChannelType() {
        return "EMAIL";
    }
}
