package com.roms.module.user.validation.validator;

import com.roms.module.user.domain.dto.ChangeEmailDto;
import com.roms.module.user.validation.constraints.ChangeEmailConfirm;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChangeEmailConfirmValidator implements ConstraintValidator<ChangeEmailConfirm, ChangeEmailDto> {

    @Override
    public void initialize(ChangeEmailConfirm constraintAnnotation) { }

    @Override
    public boolean isValid(ChangeEmailDto dto, ConstraintValidatorContext context) {
        if (! dto.getEmail().equals(dto.getEmailConfirm())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("error.change_email.email_confirm")
                    .addPropertyNode("emailConfirm")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
