package com.roms.library.controller.advice;

import com.roms.library.controller.error.dto.RestErrorMessageDto;
import com.roms.library.exception.FormErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FormErrorHandler {

	@ExceptionHandler(FormErrorException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RestErrorMessageDto processValidationError(FormErrorException exception) {
		return new RestErrorMessageDto("Wrong form", exception.getMessageCode());
	}

}
