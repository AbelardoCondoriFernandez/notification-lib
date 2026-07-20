package pe.com.example.core;

import lombok.Getter;
import pe.com.example.api.Notification;
import pe.com.example.api.NotificationChannel;
import pe.com.example.api.NotificationResult;
import pe.com.example.channels.EmailChannel;
import pe.com.example.channels.PushChannel;
import pe.com.example.channels.SmsChannel;
import pe.com.example.config.NotificationConfig;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class NotificationService {
    private final ChannelRegistry registry;
    private final ExecutorService executor;
    @Getter
    private final NotificationConfig config;

    public NotificationService(NotificationConfig config) {
        this.config = config;
        this.registry = new ChannelRegistry();
        this.executor = Executors.newFixedThreadPool(config.getAsyncPoolSize());
        registerChannels();
    }

    private void registerChannels() {
        if (config.getSendGridConfig() != null) {
            var provider = ProviderFactory.createProvider(config, "EMAIL");
            var channel = new EmailChannel(provider);
            registry.registerChannel("EMAIL", channel);
        }
        if (config.getTwilioConfig() != null) {
            var provider = ProviderFactory.createProvider(config, "SMS");
            var channel = new SmsChannel(provider);
            registry.registerChannel("SMS", channel);
        }
        if (config.getFcmConfig() != null) {
            var provider = ProviderFactory.createProvider(config, "PUSH");
            var channel = new PushChannel(provider);
            registry.registerChannel("PUSH", channel);
        }
    }

    public NotificationResult send(String channelName, Notification notification) {
        NotificationChannel channel = registry.getChannel(channelName);
        return channel.send(notification);
    }

    public CompletableFuture<NotificationResult> sendAsync(String channelName, Notification notification) {
        NotificationChannel channel = registry.getChannel(channelName);
        return CompletableFuture.supplyAsync(() -> channel.send(notification), executor);
    }

    public List<CompletableFuture<NotificationResult>> sendBatchAsync(String channelName, List<Notification> notifications) {
        return notifications.stream()
                .map(n -> sendAsync(channelName, n))
                .collect(Collectors.toList());
    }

    public void shutdown() {
        executor.shutdown();
    }
}