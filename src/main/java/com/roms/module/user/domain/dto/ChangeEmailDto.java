package com.roms.module.user.domain.dto;

import com.roms.library.validation.constraints.Unique;
import com.roms.module.user.validation.constraints.ChangeEmailConfirm;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@ChangeEmailConfirm()
public class ChangeEmailDto {

	@NotBlank
    @Email
    @Unique(daoName = "userDao", fieldName = "email")
    private String email;

    @NotBlank
    @Email
    private String emailConfirm;

    @NotBlank
    @URL
    private String activationUrl;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailConfirm() {
        return emailConfirm;
    }

    public void setEmailConfirm(String emailConfirm) {
        this.emailConfirm = emailConfirm;
    }

    public String getActivationUrl() {
        return activationUrl;
    }

    public void setActivationUrl(String activationUrl) {
        this.activationUrl = activationUrl;
    }

}
