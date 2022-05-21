package ds.assignment.dtos.rest.validators;

import ds.assignment.dtos.rest.validators.annotations.UsernameConstraint;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return username != null &&
                username.matches("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,28}[a-zA-Z0-9]$");
    }
}
