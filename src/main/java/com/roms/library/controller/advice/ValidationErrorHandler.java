package com.roms.library.controller.advice;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.roms.library.controller.error.dto.RestErrorMessageDto;
import com.roms.library.controller.error.dto.RestFieldErrorsDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ValidationErrorHandler
{
	private static final String BAD_REQUEST_ERROR_CODE = "invalid_value";

	private RestErrorMessageDto restErrors;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RestErrorMessageDto processValidationError(MethodArgumentNotValidException exception) {
		
		BindingResult result = exception.getBindingResult();
		List<FieldError> errors = result.getFieldErrors();
		
		this.restErrors = new RestErrorMessageDto("Validation of DTO '" + result.getObjectName() + "' has fail", BAD_REQUEST_ERROR_CODE);
		
		this.processError(errors);
		
		return this.restErrors;
	}
	
	private void processError(List<FieldError> errors)
	{
		for (FieldError error : errors) {
			this.restErrors.addError(this.processFieldErrors(error));
		}
	}
    
	private RestFieldErrorsDto processFieldErrors(FieldError error) 
	{
		if (error == null) {
			return null;
		}

		return new RestFieldErrorsDto(error.getField(), error.getCode(), error.getDefaultMessage());
	}
	
}
