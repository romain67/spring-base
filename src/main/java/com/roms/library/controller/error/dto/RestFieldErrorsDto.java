package com.roms.library.controller.error.dto;

/**
 * User to populate REST errors
 * 
 * @author Roman Seignez
 */
public class RestFieldErrorsDto 
{
	private String field;
	
	private String errorCode;
	
	private String errorMessage;
	
	public RestFieldErrorsDto(String field, String errorCode, String errorMessage) 
	{
		this.field = field;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
