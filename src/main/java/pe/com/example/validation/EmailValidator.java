package pe.com.example.validation;

import pe.com.example.exceptions.ValidationException;

import java.util.regex.Pattern;

public class EmailValidator implements Validator<String> {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public void validate(String email) throws ValidationException {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email address: " + email);
        }
    }
}
