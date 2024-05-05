package com.ldms.loanManager.advise;

import com.ldms.loanManager.exception.EnquiryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler
    public Map<String, String> handleInvalidArg(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach( er -> {
            errors.put(er.getField(),er.getDefaultMessage());
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler
    public Map<String, String> handleEnquiryNotFound(EnquiryNotFoundException ex){
        Map<String, String> error = new HashMap<>();
        error.put("errorMessage",ex.getMessage());
        return error;
    }
}
