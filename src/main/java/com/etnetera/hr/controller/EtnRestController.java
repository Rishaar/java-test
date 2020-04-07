package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.rest.Errors;
import com.etnetera.hr.rest.ValidationError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main REST controller.
 *
 * @author Etnetera
 *
 */
public abstract class EtnRestController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Errors> handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		Errors errors = new Errors();
		List<ValidationError> errorList = result.getFieldErrors().stream().map(e -> {
			return new ValidationError(e.getField(), e.getCode());
		}).collect(Collectors.toList());
		errors.setErrors(errorList);
		return ResponseEntity.badRequest().body(errors);
	}
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Errors> handleDataIntegrityViolationException(JavaScriptFramework framework) {
		Errors errors = new Errors();
		List<ValidationError> errorList = new ArrayList<>();
		if (framework.getName() == null) {
			ValidationError validationError = new ValidationError("name", "NotEmpty");
			errorList.add(validationError);
		} else if (framework.getName().length() > 30) {
			ValidationError validationError = new ValidationError("name", "Size");
			errorList.add(validationError);
		}
		errors.setErrors(errorList);
		return ResponseEntity.badRequest().body(errors);
	}

}
