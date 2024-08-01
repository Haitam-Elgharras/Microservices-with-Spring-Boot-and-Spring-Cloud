package com.in28.socialmedia28.advice;

import com.in28.socialmedia28.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(HttpServletRequest req, UserNotFoundException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
        response.setMessage(ex.getMessage() + " " + req.getRequestURI());

        return buildResponseEntity(response);
    }


    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);

        String message = ex.getFieldErrors().stream().map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).reduce("", (acc, elem) -> acc + elem + " and ");
        response.setMessage("Error count " + ex.getErrorCount() + " " + message);

        return buildResponseEntity(response);
    }


    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse){
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
