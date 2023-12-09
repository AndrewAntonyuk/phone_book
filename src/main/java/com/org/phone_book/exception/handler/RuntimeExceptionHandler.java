package com.org.phone_book.exception.handler;

import com.org.phone_book.exception.PhoneBookEntriesNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RuntimeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {PhoneBookEntriesNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(Exception exception, WebRequest request){
        var responseBody = exception.getMessage();

        return handleExceptionInternal(
                exception,
                responseBody,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request
        );
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleBaseException(Exception exception, WebRequest request){
        var responseBody = exception.getMessage();

        return handleExceptionInternal(
                exception,
                responseBody,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }
}
