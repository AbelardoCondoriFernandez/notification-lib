package pe.com.example;

import pe.com.example.api.EmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.com.example.api.NotificationResult;
import pe.com.example.api.PushNotification;
import pe.com.example.api.SmsNotification;
import pe.com.example.config.FcmConfig;
import pe.com.example.config.NotificationConfig;
import pe.com.example.config.SendGridConfig;
import pe.com.example.config.TwilioConfig;
import pe.com.example.core.NotificationService;


public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Configuración con variables de entorno (mejores prácticas)
        SendGridConfig sgConfig = SendGridConfig.builder()
                .apiKey(System.getenv().getOrDefault("SENDGRID_API_KEY", "fake-key"))
                .fromEmail(System.getenv().getOrDefault("FROM_EMAIL", "abelcgstone@gmail.com"))
                .build();

        TwilioConfig twilioConfig = TwilioConfig.builder()
                .accountSid(System.getenv().getOrDefault("TWILIO_SID", "fake-sid"))
                .authToken(System.getenv().getOrDefault("TWILIO_TOKEN", "fake-token"))
                .fromNumber(System.getenv().getOrDefault("TWILIO_FROM", "+123456789"))
                .build();

        FcmConfig fcmConfig = FcmConfig.builder()
                .serviceAccountKeyPath(System.getenv().getOrDefault("FCM_KEY_PATH", "/fake/path.json"))
                .build();

        NotificationConfig config = NotificationConfig.builder()
                .sendGridConfig(sgConfig)
                .twilioConfig(twilioConfig)
                .fcmConfig(fcmConfig)
                .asyncPoolSize(4)
                .build();

        NotificationService service = new NotificationService(config);

        // Crear notificación para el correo receptor
        EmailNotification email = EmailNotification.builder()
                .to("abelardocf0110@gmail.com")   // Correo de destino
                .subject("Prueba desde Notification Library")
                .body("Este es un correo de prueba con la librería.")
                .build();
        NotificationResult emailResult = service.send("EMAIL", email);
        log.info("Email result: success={}, msg={}", emailResult.isSuccess(), emailResult.getMessage());

        // --- SMS ---
        SmsNotification sms = SmsNotification.builder()
                .phoneNumber("+1234567890")
                .body("Hello SMS!")
                .build();
        NotificationResult smsResult = service.send("SMS", sms);
        log.info("SMS result: success={}, msg={}", smsResult.isSuccess(), smsResult.getMessage());

        // --- Push ---
        PushNotification push = PushNotification.builder()
                .deviceToken("valid-device-token-123")
                .title("Push Title")
                .body("Push Body")
                .build();
        NotificationResult pushResult = service.send("PUSH", push);
        log.info("Push result: success={}, msg={}", pushResult.isSuccess(), pushResult.getMessage());

        // --- Async example ---
        service.sendAsync("EMAIL", email)
                .thenAccept(r -> log.info("Async result: {}", r.isSuccess()))
                .join();

        service.shutdown();
    }
}

