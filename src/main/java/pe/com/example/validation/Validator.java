package pe.com.example.validation;


import pe.com.example.exceptions.ValidationException;

@FunctionalInterface
public interface Validator<T> {
    void validate(T target) throws ValidationException;
}
