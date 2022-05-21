package ds.assignment.dtos.rest.validators;

import ds.assignment.dtos.rest.validators.annotations.AddressConstraint;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class AddressValidator implements ConstraintValidator<AddressConstraint, String> {

    @Override
    public void initialize(AddressConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String address, ConstraintValidatorContext constraintValidatorContext) {
        return address != null &&
                !address.matches("^(?=.*[!@#\\$%\\^&\\*()])");
    }
}
