package io.jianxun.common.web;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.jianxun.business.web.dto.ReturnDto;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	@Autowired
	private MessageSource message;

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ReturnDto processValidationError(BindException ex) {
		BindingResult result = ex.getBindingResult();
		FieldError error = result.getFieldError();

		return processFieldError(error);
	}

	private ReturnDto processFieldError(FieldError error) {
		ReturnDto re = null;
		if (error != null) {
			String code = error.getDefaultMessage();
			String msg = message.getMessage(code, null, Locale.CHINA);
			re = ReturnDto.error(msg);
		}
		return re;
	}

}
