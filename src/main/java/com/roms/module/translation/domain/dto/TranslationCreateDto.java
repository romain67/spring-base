package com.roms.module.translation.domain.dto;

import com.roms.module.translation.validation.constraints.TranslationLanguage;
import com.roms.module.translation.validation.constraints.TranslationUnique;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

interface validLanguageFirst {}

@TranslationLanguage(groups = validLanguageFirst.class)
@TranslationUnique()
@GroupSequence({validLanguageFirst.class, TranslationCreateDto.class})
public class TranslationCreateDto implements TranslationDto {

    @Length(max = 50)
    @NotBlank
    private String code;

    @NotNull
    private String language;

    @NotBlank
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
