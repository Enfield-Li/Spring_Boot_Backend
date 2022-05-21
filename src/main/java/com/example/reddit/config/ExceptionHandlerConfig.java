package com.example.reddit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerConfig extends RuntimeException {

  private static final Logger log = LoggerFactory.getLogger(
    ExceptionHandlerConfig.class
  );

  @ExceptionHandler(value = { Exception.class })
  protected ResponseEntity<?> catchAllException(Exception e) {
    log.error("\n \n !!!Error!!!", e);

    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body("Something's gone wrong...");
  }
}
