package pe.com.example.channels;

import lombok.RequiredArgsConstructor;
import pe.com.example.api.Notification;
import pe.com.example.api.NotificationChannel;
import pe.com.example.api.NotificationProvider;
import pe.com.example.api.NotificationResult;

@RequiredArgsConstructor
public class PushChannel implements NotificationChannel {
    private final NotificationProvider provider;

    @Override
    public NotificationResult send(Notification notification) {
        return provider.send(notification);
    }

    @Override
    public String getChannelName() {
        return "Push";
    }
}