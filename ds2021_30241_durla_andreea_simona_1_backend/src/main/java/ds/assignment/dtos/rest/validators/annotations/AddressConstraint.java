package ds.assignment.dtos.rest.validators.annotations;

import ds.assignment.dtos.rest.validators.AddressValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AddressValidator.class})
public @interface AddressConstraint {

    String message() default "Invalid address (cannot contain) " +
                            "special characters \"!@#$%^&*()\")";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
