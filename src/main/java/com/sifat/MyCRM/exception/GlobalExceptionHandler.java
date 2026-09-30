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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModelDTO> handleException(
            Exception ex,
            HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getExceptionResponseModelDTO(ex.getMessage(),request.getRequestURI(),null));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseModelDTO> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(getExceptionResponseModelDTO(ex.getMessage(),request.getRequestURI(),null));
    }

    private ResponseModelDTO getExceptionResponseModelDTO(String msg,String reqPath,Object data){
        return new ResponseModelDTO(
                ResponseDataStatus.error.name(),
                msg,
                reqPath,
                LocalDateTime.now(),
                data
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseModelDTO> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .badRequest()
                .body(getExceptionResponseModelDTO("Validation failed",request.getRequestURI(),errors));
    }
}