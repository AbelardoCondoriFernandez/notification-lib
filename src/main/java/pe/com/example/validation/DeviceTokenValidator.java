package pe.com.example.validation;

import pe.com.example.exceptions.ValidationException;

import java.util.regex.Pattern;

public class DeviceTokenValidator implements Validator<String> {
    // Patrón simple para simular un token FCM (puedes refinarlo)
    private static final Pattern TOKEN_PATTERN = Pattern.compile("^[a-zA-Z0-9:\\-._~]+$");

    @Override
    public void validate(String token) throws ValidationException {
        if (token == null || !TOKEN_PATTERN.matcher(token).matches()) {
            throw new ValidationException("Invalid device token: " + token);
        }
    }
}