package com.product.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


//Manejador que le dice a spring boot como interpretar las exceptions sin importar 
//donde aparezcan 

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	
	
	
	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<ExceptionResponse> handleApiException(ApiException exception, WebRequest request){
		
		
		//Establecemos atributos de ExceptionResponse
		ExceptionResponse response = new ExceptionResponse();
		
		response.setTimestamp(LocalDateTime.now());
		response.setStatus(exception.getStatus().value());
		response.setError(exception.getStatus());
		response.setMessage(exception.getMessage());
		response.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());
		
		// Retornamos un el ResponseEntity que sirve para respuestas HTTP 
		return new ResponseEntity<>(response, response.getError());
	}
	
	

}