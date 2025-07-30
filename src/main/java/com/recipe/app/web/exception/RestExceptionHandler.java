package com.recipe.app.web.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.recipe.app.service.dto.MessageResponse;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<MessageResponse> handleConflict(DataIntegrityViolationException ex) {
    return ResponseEntity
        .badRequest()
        .body(new MessageResponse(ex.getMessage()));
  }
}
