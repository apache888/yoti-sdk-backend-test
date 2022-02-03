package com.yoti.hoover.controller;

import com.yoti.hoover.exception.CoordinatesOutOfRoomDimensionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GlobalExceptionHandler.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getConstraintViolations().forEach(cv -> errors.add(cv.getPropertyPath().toString().concat(" - ").concat(cv.getMessage())));

        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CoordinatesOutOfRoomDimensionException.class)
    public ResponseEntity<?> handleCoordinatesOutOfRoomDimensionException(CoordinatesOutOfRoomDimensionException ex, WebRequest request) {
        Map<String, String> result = new HashMap<>();
        result.put("error", ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, String> result = new HashMap<>();
        result.put("error", ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> result = new HashMap<>();
        result.put("error", ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "Field ".concat(fieldError.getField()).concat("-")
                        .concat(fieldError.getRejectedValue() == null ? "NULL" : fieldError.getRejectedValue().toString()).concat("-")
                        .concat(StringUtils.hasText(fieldError.getDefaultMessage()) ? fieldError.getDefaultMessage() : "No message"))
                .collect(Collectors.joining("; ")));
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> result = new HashMap<>();
        result.put("error", ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
