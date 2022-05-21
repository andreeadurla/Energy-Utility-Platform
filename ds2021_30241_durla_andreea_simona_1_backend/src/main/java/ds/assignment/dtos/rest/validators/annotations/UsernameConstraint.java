package ds.assignment.dtos.rest.validators.annotations;

import ds.assignment.dtos.rest.validators.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UsernameValidator.class})
public @interface UsernameConstraint {

    String message() default "Invalid username (must contain " +
                            "between 5 and 30 characters and can contain " +
                            "only letters, digits and \"._-\")";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
