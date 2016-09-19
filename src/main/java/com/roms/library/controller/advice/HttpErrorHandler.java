package com.roms.library.controller.advice;

import com.roms.library.http.exception.NotFoundException;
import com.roms.module.user.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HttpErrorHandler {

	private static final Logger logger = LogManager.getLogger(AccountService.class);

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public String processNotFound(NotFoundException exception) {
        logger.error(exception.getMessage());
		return HttpStatus.NOT_FOUND.getReasonPhrase();
	}

}
