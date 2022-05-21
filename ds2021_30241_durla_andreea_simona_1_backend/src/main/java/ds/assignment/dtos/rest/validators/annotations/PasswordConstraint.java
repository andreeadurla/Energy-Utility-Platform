package ds.assignment.dtos.rest.validators.annotations;

import ds.assignment.dtos.rest.validators.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PasswordValidator.class})
public @interface PasswordConstraint {

    String message() default "Invalid password (Must contain lowercase, " +
                            "uppercase, numeric, special character and " +
                            "must be eight characters or longer)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
