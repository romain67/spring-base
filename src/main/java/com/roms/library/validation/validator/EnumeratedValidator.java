package com.roms.library.validation.validator;

import com.roms.library.validation.constraints.Enumerated;
import org.apache.commons.lang3.EnumUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumeratedValidator implements ConstraintValidator<Enumerated, String> {

    private Class<? extends Enum<?>> enumClass;
    private String message;

    @Override
    public void initialize(Enumerated constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClazz();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = EnumUtils.isValidEnum( (Class) this.enumClass, value);
//
//        if (! isValid) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("Translation already exists")
//                    .addPropertyNode("code")
//                    .addConstraintViolation();
//        }

        return isValid;
    }

}
