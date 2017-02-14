package com.roms.module.user.domain.dto;

import com.roms.module.user.validation.constraints.ResetPasswordPasswordConfirm;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@ResetPasswordPasswordConfirm()
public class ResetPasswordDto {

	@NotBlank
	@Length(min = 8, max = 255)
	private String password;

	@NotBlank
	@Length(min = 8, max = 255)
	private String passwordConfirm;

    @NotBlank
    @Length(min = 32, max = 32)
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
