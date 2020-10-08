package com.thoughtworks.rslist.exception;

import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler({IndexOutOfBoundsException.class, MethodArgumentNotValidException.class, IndexOutOfBoundsExceptionWhenGetOne.class})
    public ResponseEntity exceptionHandler(Exception exception) {

        CommentError commentError = new CommentError();
        Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
        String errorLog = "";

        if(exception instanceof IndexOutOfBoundsException){
            errorLog = "invalid request param";
        }

        if(exception instanceof IndexOutOfBoundsExceptionWhenGetOne){
            errorLog = "invalid index";
        }

        if(exception instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) exception;
            if(methodArgumentNotValidException.getBindingResult().getTarget() instanceof RsEventDto){
                errorLog = "invalid param";
            }
            if(methodArgumentNotValidException.getBindingResult().getTarget() instanceof UserDto){
                errorLog = "invalid user";
            }
        }

        if (errorLog == "") {
            errorLog = exception.getMessage();
        }

        commentError.setError(errorLog);
        logger.error("[ErrorLog]: " + errorLog);
        return ResponseEntity.badRequest().body(commentError);
    }
}
