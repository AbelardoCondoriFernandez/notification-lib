package pe.com.example.validation;

import pe.com.example.exceptions.ValidationException;

import java.util.regex.Pattern;

public class PhoneValidator implements Validator<String> {
    // Patrón sencillo para números internacionales (puedes refinarlo)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");

    @Override
    public void validate(String phone) throws ValidationException {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            throw new ValidationException("Invalid phone number: " + phone);
        }
    }
}