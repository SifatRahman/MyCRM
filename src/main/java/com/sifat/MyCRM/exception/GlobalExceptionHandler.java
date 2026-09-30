package com.sifat.MyCRM.exception;

import com.sifat.MyCRM.dto.helper.ResponseModelDTO;
import com.sifat.MyCRM.utility.ResponseDataStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModelDTO> handleException(
            Exception ex,
            HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getExceptionResponseModelDTO(ex.getMessage(),request.getRequestURI()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseModelDTO> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(getExceptionResponseModelDTO(ex.getMessage(),request.getRequestURI()));
    }

    private ResponseModelDTO getExceptionResponseModelDTO(String msg,String reqPath){
        return new ResponseModelDTO(
                ResponseDataStatus.error.name(),
                msg,
                reqPath,
                LocalDateTime.now(),
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseModelDTO> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage()
                )
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .badRequest()
                .body(getExceptionResponseModelDTO(ex.getMessage(),request.getRequestURI()));
    }
}