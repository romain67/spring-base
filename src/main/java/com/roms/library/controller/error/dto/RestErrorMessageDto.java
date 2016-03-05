package com.roms.library.controller.error.dto;

import java.util.ArrayList;

/**
 * User to populate REST errors
 * 
 * @author Roman Seignez
 */
public class RestErrorMessageDto 
{
	private String message;
	
	private String errorCode;
	
	private ArrayList<RestFieldErrorsDto> errors = new ArrayList<RestFieldErrorsDto>();
	
	public RestErrorMessageDto(String message, String errorCode) 
	{
		this.setMessage(message);
		this.setErrorCode(errorCode);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public ArrayList<RestFieldErrorsDto> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<RestFieldErrorsDto> errors) {
		this.errors = errors;
	}
	
	public void addError(RestFieldErrorsDto error)
	{
		this.errors.add(error);
	}
	
}
