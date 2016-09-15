package com.roms.module.translation.validation.constraints;

import com.roms.module.translation.validation.validator.TranslationUniqueValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = TranslationUniqueValidator.class)
@Documented
public @interface TranslationUnique {

    String message() default "{error.translation.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
