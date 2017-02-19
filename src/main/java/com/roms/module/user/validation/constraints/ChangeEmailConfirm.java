package com.roms.module.user.validation.constraints;

import com.roms.module.user.validation.validator.ChangeEmailConfirmValidator;
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
@Constraint(validatedBy = ChangeEmailConfirmValidator.class)
@Documented
public @interface ChangeEmailConfirm {

    String message() default "{error.change_email.email_confirm}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
