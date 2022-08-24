package com.example.logbacktest.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.logbacktest.config.aop.RequestLogAspect;
import com.example.logbacktest.constant.ErrorCode;
import com.example.logbacktest.dto.ApiErrorResponse;
import com.example.logbacktest.exception.GeneralException;

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

	@ExceptionHandler
	public ResponseEntity<Object> general(
		GeneralException e,
		WebRequest request
	) {
		ErrorCode errorCode = e.getErrorCode();
		HttpStatus status = errorCode.isClientSideError()
			? HttpStatus.BAD_REQUEST
			: HttpStatus.INTERNAL_SERVER_ERROR;
		return handleExceptionInternal(e, errorCode, HttpHeaders.EMPTY, status, request);
	}

	@ExceptionHandler
	public ResponseEntity<Object> exception(
		Exception e,
		WebRequest request
	) {
		return handleExceptionInternal(
			e, ErrorCode.INTERNAL_ERROR, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
		Exception ex,
		Object body,
		HttpHeaders headers,
		HttpStatus status,
		WebRequest request
	) {
		ErrorCode errorCode = status.is4xxClientError()
			? ErrorCode.BAD_REQUEST
			: ErrorCode.INTERNAL_ERROR;
		return handleExceptionInternal(ex, errorCode, headers, status, request);
	}

	private ResponseEntity<Object> handleExceptionInternal(
		Exception e,
		ErrorCode errorCode,
		HttpHeaders headers,
		HttpStatus status,
		WebRequest request
	) {
		logger.info("======================================================================");
		logger.info("Exception: {} - {}", errorCode.getCode(), errorCode.getMessage());
		e.printStackTrace();

		return super.handleExceptionInternal(e,
			ApiErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)), headers, status, request
		);
	}
}
