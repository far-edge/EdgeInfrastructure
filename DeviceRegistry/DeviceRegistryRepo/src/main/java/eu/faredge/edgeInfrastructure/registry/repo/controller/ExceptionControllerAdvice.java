package eu.faredge.edgeInfrastructure.registry.repo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception ex) {
		String error;
//		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error = "Please contact your administrator";
		return new ResponseEntity<String>(error, HttpStatus.BAD_REQUEST);
	}
}
