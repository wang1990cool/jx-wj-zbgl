package io.jianxun.common.web;

import java.util.List;
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
		return processFieldError(result.getFieldErrors());
	}

	private ReturnDto processFieldError(List<FieldError> errors) {
		ReturnDto re = null;
		if (errors != null && errors.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (FieldError error : errors) {
				String msg = message.getMessage(error.getCode(), error.getArguments(), Locale.CHINA);
				sb.append(msg + "<br />");

			}
			re = ReturnDto.error(sb.toString());
		}
		return re;
	}

}
