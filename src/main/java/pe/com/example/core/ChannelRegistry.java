package pe.com.example.core;

import pe.com.example.api.NotificationChannel;

import java.util.HashMap;
import java.util.Map;

public class ChannelRegistry {
    private final Map<String, NotificationChannel> channels = new HashMap<>();

    public void registerChannel(String name, NotificationChannel channel) {
        channels.put(name, channel);
    }

    public NotificationChannel getChannel(String name) {
        NotificationChannel channel = channels.get(name);
        if (channel == null) {
            throw new IllegalArgumentException("Channel not found: " + name);
        }
        return channel;
    }
}
