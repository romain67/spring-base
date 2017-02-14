package com.roms.module.user.validation.validator;

import com.roms.module.user.domain.dto.ResetPasswordDto;
import com.roms.module.user.validation.constraints.ResetPasswordPasswordConfirm;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ResetPasswordPasswordConfirmValidator
        implements ConstraintValidator<ResetPasswordPasswordConfirm, ResetPasswordDto> {

    @Override
    public void initialize(ResetPasswordPasswordConfirm constraintAnnotation) { }

    @Override
    public boolean isValid(ResetPasswordDto dto, ConstraintValidatorContext context) {
        if (! dto.getPassword().equals(dto.getPasswordConfirm())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("error.reset_password.password_confirm")
                    .addPropertyNode("passwordConfirm")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
