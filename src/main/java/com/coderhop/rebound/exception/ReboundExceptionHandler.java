package com.coderhop.rebound.exception;

import java.util.Date;

import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coderhop.rebound.exception.model.BadRequestException;
import com.coderhop.rebound.exception.model.BaseExceptionMessage;
import com.coderhop.rebound.exception.model.DataNotFoundException;
import com.coderhop.rebound.exception.model.DefaultExceptionMessage;
import com.coderhop.rebound.exception.model.ExceptionMessage;
import com.coderhop.rebound.exception.model.ForbiddenException;
import com.coderhop.rebound.exception.model.SystemException;
import com.coderhop.rebound.exception.model.UnauthorizedException;

@ControllerAdvice
@RestController
public class ReboundExceptionHandler extends ResponseEntityExceptionHandler {
	@Value("${rebound.internal.error.msg:Internal Sever Error : Please Try later[Refer logs for details]}")
	String SYSTEM_ERROR_OCCURED_REFER_LOGS_FOR_DETAILS;
	@Value("${rebound.app.transactionid.key:traceId}")
	String transactionIdKey;

	/***
	 * Checked User Error : GET API failed to fetch required Data
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<BaseExceptionMessage> handleDataNotFound(Exception exception, WebRequest request) {
		DataNotFoundException dataNotFoundException = (DataNotFoundException) exception;
		BaseExceptionMessage exceptionMessage = fromWebRequestToExceptionMessage(request, HttpStatus.NOT_FOUND,
				dataNotFoundException.getMessage(), dataNotFoundException.getAppErrorCode());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<BaseExceptionMessage> handleBadRequestException(Exception exception,
			WebRequest request) {

		BadRequestException badRequestException = (BadRequestException) exception;
		BaseExceptionMessage exceptionMessage = fromWebRequestToExceptionMessage(request, HttpStatus.BAD_REQUEST,
				badRequestException.getMessage(), badRequestException.getAppErrorCode());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ForbiddenException.class, org.springframework.security.access.AccessDeniedException.class })
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public final ResponseEntity<BaseExceptionMessage> handleForbiddenException(Exception exception,
			WebRequest request) {

		ForbiddenException forbiddenException = (ForbiddenException) exception;
		BaseExceptionMessage exceptionMessage = fromWebRequestToExceptionMessage(request, HttpStatus.FORBIDDEN,
				forbiddenException.getMessage(), forbiddenException.getAppErrorCode());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ UnauthorizedException.class,
			org.springframework.security.authentication.BadCredentialsException.class,
			AuthorizationServiceException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public final ResponseEntity<BaseExceptionMessage> handleUnauthorizedException(Exception exception,
			WebRequest request) {
		UnauthorizedException unauthorizedException = (UnauthorizedException) exception;
		BaseExceptionMessage exceptionMessage = fromWebRequestToExceptionMessage(request, HttpStatus.UNAUTHORIZED,
				unauthorizedException.getMessage(), unauthorizedException.getAppErrorCode());

		return new ResponseEntity<>(exceptionMessage, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler({ Throwable.class, IllegalStateException.class, IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<BaseExceptionMessage> handleRunTimeSystemError(RuntimeException exception,
			WebRequest request) {
		BaseExceptionMessage exceptionMessage = fromWebRequestToExceptionMessage(request,
				HttpStatus.INTERNAL_SERVER_ERROR, SYSTEM_ERROR_OCCURED_REFER_LOGS_FOR_DETAILS, null);
		return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler({ SystemException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<BaseExceptionMessage> handleWrappedRunTimeSystemError(RuntimeException exception,
			WebRequest request) {
		SystemException systemexception = (SystemException) exception;
		BaseExceptionMessage exceptionMessage = fromWebRequestToExceptionMessage(request,
				HttpStatus.INTERNAL_SERVER_ERROR, systemexception.getMessage(), systemexception.getAppErrorCode());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private BaseExceptionMessage fromWebRequestToExceptionMessage(WebRequest request, HttpStatus httpStatus,
			String errorMessage, String appErrroCode) {

		String transactionId = MDC.get(transactionIdKey) == null ? request.getSessionId() : MDC.get(transactionIdKey);
		DefaultExceptionMessage exceptionMessage = new DefaultExceptionMessage();

		exceptionMessage.setTransactionId(transactionId);
		exceptionMessage.setCategory(httpStatus.getReasonPhrase());
		exceptionMessage.setHttpErrorCode(httpStatus.value());
		// AppError code is a customization provided to client application
		exceptionMessage.setErrorMessage(errorMessage);
		exceptionMessage.setTimestamp(new Date());
		if (appErrroCode != null) {
			ExceptionMessage expMessage = new ExceptionMessage();
			BeanUtils.copyProperties(exceptionMessage, expMessage);
			expMessage.setAppErrorCode(appErrroCode);
			return expMessage;
		}

		return exceptionMessage;
	}

}