package com.galaxyit.exception;

import com.galaxyit.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static java.util.Objects.isNull;

@ControllerAdvice
public class CarExceptionHandler {

    @ExceptionHandler(value = {CarNotFoundException.class})
    public ResponseEntity<Object> handle(Exception e, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(),
                isNull(e.getLocalizedMessage()) ? "Unable to find car" : e.getLocalizedMessage());

        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


