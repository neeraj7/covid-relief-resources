package com.covid.relief.exception.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.covid.relief.exception.ApiError;
import com.covid.relief.exception.ApiRuntimeException;
import com.covid.relief.exception.ApiValidationError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

   @Override
   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
       String error = "Malformed JSON request";
       return buildResponseEntity(new ApiRuntimeException(HttpStatus.BAD_REQUEST, error, ex));
   }
   
   @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
	    List<ApiValidationError> subErrors = ex.getBindingResult().getAllErrors().stream().map((er) -> {
	    	return new ApiValidationError(((FieldError) er).getField(), er.getDefaultMessage());
	    	
	    }).collect(Collectors.toList());
	   ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Request body is not correct.");
	   error.setSubErrors(subErrors);
	   return new ResponseEntity<>(error, error.getStatus());
	}
   @ExceptionHandler(ApiRuntimeException.class)
   protected ResponseEntity<Object> handleRuntimeException(ApiRuntimeException ex) {
       return buildResponseEntity(ex);
   }

   private ResponseEntity<Object> buildResponseEntity(ApiRuntimeException ex) {
	   ApiError error = new ApiError(ex.getStatus(), ex.getMessage());
	   
       return new ResponseEntity<>(error, ex.getStatus());
   }

   //other exception handlers below

}