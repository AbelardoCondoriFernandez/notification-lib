package pe.com.example.api;

import java.util.concurrent.CompletableFuture;

public interface NotificationChannel {
    NotificationResult send(Notification notification);
    default CompletableFuture<NotificationResult> sendAsync(Notification notification) {
        return CompletableFuture.supplyAsync(() -> send(notification));
    }
    String getChannelName();
}
