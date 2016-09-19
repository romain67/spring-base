package com.roms.module.user.domain.dto;

import com.roms.library.validation.constraints.Unique;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class UserRegisterDto {

	@Length(min = 2, max = 255)
	private String firstName;

	@Length(min = 2, max = 255)
	private String lastName;

	@NotBlank
	@Length(min = 3, max = 255)
    @Unique(daoName = "userDao", fieldName = "username")
	private String username;

	@NotBlank
	@Email
    @Unique(daoName = "userDao", fieldName = "email")
	private String email;

	@NotBlank
	@Length(min = 8, max = 255)
	private String password;

	@NotBlank
	@Length(min = 8, max = 255)
	private String passwordConfirm;

    @NotBlank
    @URL
    private String activationUrl;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getActivationUrl() {
        return activationUrl;
    }

    public void setActivationUrl(String activationUrl) {
        this.activationUrl = activationUrl;
    }

}
