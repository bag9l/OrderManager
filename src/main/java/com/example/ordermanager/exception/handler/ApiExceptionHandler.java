package com.example.ordermanager.exception.handler;

import com.example.ordermanager.exception.EntityNotExistsException;
import com.example.ordermanager.exception.OutOfQuantityException;
import com.example.ordermanager.exception.PermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(err -> {
            errorMap.put(err.getField(), err.getDefaultMessage());
        });
        return errorMap;
    }

    @ExceptionHandler(EntityNotExistsException.class)
    protected ResponseEntity<Object> handleEntityNotExists(
            EntityNotExistsException exception) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(PermissionException.class)
    protected ResponseEntity<Object> handlePermissionException(
            PermissionException exception) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(OutOfQuantityException.class)
    protected ResponseEntity<Object> handleOutOfQuantityException(
            OutOfQuantityException exception) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }
}
