package com.roms.module.translation.validation.validator;

import com.roms.module.translation.domain.dto.TranslationDto;
import com.roms.module.translation.service.TranslationService;
import com.roms.module.translation.validation.constraints.TranslationUnique;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TranslationUniqueValidator implements ConstraintValidator<TranslationUnique, TranslationDto> {

    @Autowired
    private TranslationService translationService;

    @Override
    public void initialize(TranslationUnique constraintAnnotation) { }

    @Override
    public boolean isValid(TranslationDto dto, ConstraintValidatorContext context) {
        boolean isValid = translationService.isUnique(dto);
        if (! isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("error.translation.unique")
                    .addPropertyNode("code")
                    .addConstraintViolation();
        }

        return isValid;
    }

}
