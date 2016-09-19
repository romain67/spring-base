package com.roms.module.user.validation.validator;

import com.roms.module.user.domain.dto.UserRegisterDto;
import com.roms.module.user.validation.constraints.RegisterPasswordConfirm;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegisterPasswordConfirmValidator implements ConstraintValidator<RegisterPasswordConfirm, UserRegisterDto> {

    @Override
    public void initialize(RegisterPasswordConfirm constraintAnnotation) { }

    @Override
    public boolean isValid(UserRegisterDto dto, ConstraintValidatorContext context) {
        if (! dto.getPassword().equals(dto.getPasswordConfirm())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("error.register.password_confirm")
                    .addPropertyNode("passwordConfirm")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
