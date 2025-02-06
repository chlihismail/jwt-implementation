package com.cxi.see_rest.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxi.see_rest.dto.ErrorDto;

@ControllerAdvice
public class RestExceptionHandler{

    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex){
        return ResponseEntity
        .status(ex.getHttpStatus())
        .body(ErrorDto.builder().message(ex.getMessage()).build());
    }
}
