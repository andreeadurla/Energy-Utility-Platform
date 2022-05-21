package ds.assignment.dtos.rest.validators;

import ds.assignment.dtos.rest.validators.annotations.NameConstraint;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NameValidator implements ConstraintValidator<NameConstraint, String> {

    @Override
    public void initialize(NameConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name != null &&
                name.matches("^[a-zA-Z](['\\- ](?!['\\- ])|[a-zA-Z]){1,48}[a-zA-Z]$");
    }
}
