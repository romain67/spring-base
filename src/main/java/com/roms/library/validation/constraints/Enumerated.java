package com.roms.library.validation.constraints;

import com.roms.library.validation.validator.EnumeratedValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = EnumeratedValidator.class)
@Documented
public @interface Enumerated {

    String message() default "{constraints.enumerated}";

    Class<? extends Enum<?>> enumClazz();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
