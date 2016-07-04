package com.roms.library.validation.validator;

import com.roms.library.dao.UniqueValidable;
import com.roms.library.validation.constraints.Unique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

    @Autowired
    private ApplicationContext applicationContext;

    private UniqueValidable dao;
    private String fieldName;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.dao = (UniqueValidable) applicationContext.getBean(constraintAnnotation.daoName());
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return dao.isUnique(fieldName, value);
    }

}
