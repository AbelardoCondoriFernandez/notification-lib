package pe.com.example.core;

import pe.com.example.api.NotificationProvider;
import pe.com.example.config.NotificationConfig;
import pe.com.example.providers.fcm.FcmProvider;
import pe.com.example.providers.sendgrid.SendGridProvider;
import pe.com.example.providers.twilio.TwilioProvider;


public class ProviderFactory {

    public static NotificationProvider createProvider(NotificationConfig config, String channelKey) {
        switch (channelKey) {
            case "EMAIL":
                if (config.getSendGridConfig() == null) {
                    throw new IllegalArgumentException("SendGrid config not found for EMAIL channel");
                }
                return new SendGridProvider(config.getSendGridConfig());
            case "SMS":
                if (config.getTwilioConfig() == null) {
                    throw new IllegalArgumentException("Twilio config not found for SMS channel");
                }
                return new TwilioProvider(config.getTwilioConfig());
            case "PUSH":
                if (config.getFcmConfig() == null) {
                    throw new IllegalArgumentException("FCM config not found for PUSH channel");
                }
                return new FcmProvider(config.getFcmConfig());
            default:
                throw new IllegalArgumentException("No provider for channel key: " + channelKey);
        }
    }
}