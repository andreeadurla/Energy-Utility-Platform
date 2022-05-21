package ds.assignment.dtos.rest.validators.annotations;

import ds.assignment.dtos.rest.validators.NameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {NameValidator.class})
public @interface NameConstraint {

    String message() default "Invalid name (must contain " +
                        "between 3 and 50 characters and can contain " +
                        "only letters and \"'- \")";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
