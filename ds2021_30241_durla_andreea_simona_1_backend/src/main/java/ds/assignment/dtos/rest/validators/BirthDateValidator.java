package ds.assignment.dtos.rest.validators;

import ds.assignment.dtos.rest.validators.annotations.BirthDateConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, Date> {

    private Date minDate;
    private Date maxDate;

    @Override
    public void initialize(BirthDateConstraint constraintAnnotation) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, -100);
        this.minDate = cal.getTime();

        this.maxDate = new Date();
    }

    @Override
    public boolean isValid(Date birthDate, ConstraintValidatorContext constraintValidatorContext) {
        return birthDate.before(maxDate) &&
                birthDate.after(minDate);
    }
}
