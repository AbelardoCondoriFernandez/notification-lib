package pe.com.example.config;

import lombok.Getter;

@Getter
public abstract class ProviderConfig {
    private final String providerName;

    protected ProviderConfig(String providerName) {
        this.providerName = providerName;
    }
}
