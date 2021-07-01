package employees;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

    private int maxLength;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null &&
                !s.isBlank() &&
                s.length() > 2 &&
                s.length() <= maxLength &&
                Character.isUpperCase(s.charAt(0));
    }

    @Override
    public void initialize(Name constraintAnnotation) {
        //ConstraintValidator.super.initialize(constraintAnnotation);
        maxLength = constraintAnnotation.maxLength();
    }
}
