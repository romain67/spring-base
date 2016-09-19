package com.roms.module.user.controller;

import com.roms.library.exception.ControllerErrorException;
import com.roms.library.exception.RegisterErrorException;
import com.roms.module.user.domain.dto.UserRegisterDto;
import com.roms.module.user.service.AccountService;
import com.roms.module.user.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController("accountController")
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

    /**
     * Register user
     */
	@RequestMapping(value="register", method = RequestMethod.POST)
	public ResponseEntity<User> register(@Validated @RequestBody UserRegisterDto registerDto) {
        try {
            User user = accountService.register(registerDto);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (RegisterErrorException e) {
            throw new ControllerErrorException(e.getMessage(), "error.account.register.user",
                    "error.account.register.user");
        }
	}

    @RequestMapping(value="activate/{token}", method = RequestMethod.GET)
    public void activate(@PathVariable("token") String token) {
        accountService.activate(token);
    }

}
