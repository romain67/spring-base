package com.roms.library.controller.advice;

import com.roms.library.controller.error.dto.RestErrorMessageDto;
import com.roms.library.exception.ControllerErrorException;
import com.roms.module.user.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerErrorHandler {

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LogManager.getLogger(AccountService.class);

	@ExceptionHandler(ControllerErrorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public RestErrorMessageDto processError(ControllerErrorException exception) {
        logger.error(exception.getMessage());

        String message = messageSource.getMessage(exception.getMessageKey(), exception.getMessageArgs(),
				LocaleContextHolder.getLocale());

        return new RestErrorMessageDto(message, exception.getErrorCode());
	}

}
