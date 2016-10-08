package io.jianxun.common.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.jianxun.business.web.dto.ReturnDto;
import io.jianxun.common.service.exception.ServiceException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ReturnDto processValidationError(BindException ex) {
		BindingResult result = ex.getBindingResult();
		return processFieldError(result.getFieldErrors());
	}

	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ReturnDto processServcieExcptionError(ServiceException ex) {
		return ReturnDto.error(ex.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ReturnDto processExcptionError(Exception ex) {
		return ReturnDto.error(ex.getMessage());
	}

	private ReturnDto processFieldError(List<FieldError> errors) {
		ReturnDto re = null;
		if (errors != null && errors.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (FieldError error : errors) {
				String msg = error.getDefaultMessage();
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + msg + "<br />");

			}
			re = ReturnDto.error(sb.toString());
		}
		return re;
	}

}
