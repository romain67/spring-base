package com.roms.module.translation.validation.constraints;

import com.roms.module.translation.validation.validator.TranslationLanguageValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = TranslationLanguageValidator.class)
@Documented
public @interface TranslationLanguage {

    String message() default "{constraints.translationLanguage}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
