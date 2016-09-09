package com.roms.module.translation.validation.validator;

import com.roms.module.translation.domain.dto.TranslationDto;
import com.roms.module.translation.domain.model.Translation;
import com.roms.module.translation.validation.constraints.TranslationLanguage;
import org.apache.commons.lang3.EnumUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TranslationLanguageValidator implements ConstraintValidator<TranslationLanguage, TranslationDto> {

    @Override
    public void initialize(TranslationLanguage constraintAnnotation) { }

    @Override
    public boolean isValid(TranslationDto dto, ConstraintValidatorContext context) {
        boolean isValid = EnumUtils.isValidEnum(Translation.AvailableLanguage.class, dto.getLanguage());
        if (! isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid language")
                    .addPropertyNode("language")
                    .addConstraintViolation();
        }

        return isValid;
    }

}
